package pers.coderaji.teez.registry.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.NamedThreadFactory;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.registry.NotifyListener;
import pers.coderaji.teez.registry.support.AbstractRegistry;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.*;
import java.util.concurrent.*;

public class RedisRegistry extends AbstractRegistry {
    private static final Logger logger = Logger.getLogger(RedisRegistry.class);

    private static final int DEFAULT_REDIS_PORT = 6379;

    private final static String DEFAULT_ROOT = Constants.TEEZ;

    private final ScheduledFuture<?> expireFuture;

    private final String root;

    private final int expirePeriod;

    private final Map<String, JedisPool> jedisPools = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, Notifier> notifiers = new ConcurrentHashMap<>();

    private final ScheduledExecutorService expireExecutor = Executors.newScheduledThreadPool(1, new NamedThreadFactory("TeezRegistryExpireTimer", true));

    public RedisRegistry(URL url) {
        super(url);
        //redis配置
        GenericObjectPoolConfig<Jedis> config = new GenericObjectPoolConfig<>();
        config.setTestOnBorrow(url.getParameter("test.on.borrow", true));
        config.setTestOnReturn(url.getParameter("test.on.return", false));
        config.setTestWhileIdle(url.getParameter("test.while.idle", false));
        config.setMaxIdle(url.getParameter("max.idle", 0));
        config.setMinIdle(url.getParameter("min.idle", 0));
        config.setMaxTotal(url.getParameter("max.active", 0));
        config.setMaxTotal(url.getParameter("max.total", 0));

        //获取redis地址列表
        List<String> addresses = new ArrayList<>();
        addresses.add(url.getAddress());
        String[] backups = url.getParameter(Constants.BACKUP, new String[0]);
        if (backups != null && backups.length > 0) {
            addresses.addAll(Arrays.asList(backups));
        }
        //保存注册中心地址
        String password = url.getPassword();
        for (String address : addresses) {
            int i = address.indexOf(':');
            String host;
            int port;
            if (i > 0) {
                host = address.substring(0, i);
                port = Integer.parseInt(address.substring(i + 1));
            } else {
                host = address;
                port = DEFAULT_REDIS_PORT;
            }
            if (ObjectUtil.isEmpty(password)) {
                this.jedisPools.put(address, new JedisPool(config, host, port,
                        url.getParameter(Constants.TIMEOUT, Constants.DEFAULT_TIMEOUT)));
            } else {
                this.jedisPools.put(address, new JedisPool(config, host, port,
                        url.getParameter(Constants.TIMEOUT, Constants.DEFAULT_TIMEOUT), password));
            }
        }
        //获取存放路径
        String group = url.getParameter(Constants.GROUP, DEFAULT_ROOT);
        if (!group.startsWith(Constants.PATH_SEPARATOR)) {
            group = Constants.PATH_SEPARATOR + group;
        }
        if (!group.endsWith(Constants.PATH_SEPARATOR)) {
            group = group + Constants.PATH_SEPARATOR;
        }
        this.root = group;
        //延时暴露服务
        this.expirePeriod = url.getParameter(Constants.SESSION, Constants.DEFAULT_SESSION_TIMEOUT);
        this.expireFuture = expireExecutor.scheduleWithFixedDelay(() -> {
            try {
                for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
                    JedisPool jedisPool = entry.getValue();
                    Jedis jedis = jedisPool.getResource();
                    try {
                        for (URL registered : new HashSet<>(getRegistered())) {
                            String key = toCategoryPath(registered);
                            if (jedis.hset(key, registered.urlString(), String.valueOf(System.currentTimeMillis() + expirePeriod)) == 1) {
                                jedis.publish(key, Constants.REGISTER);
                            }
                        }
                    } finally {
                        jedis.close();
                    }
                }
            } catch (Throwable t) {
                logger.info(String.format("unexpected exception occur at defer expire time, cause: %s", t.getMessage()), t);
            }
        }, expirePeriod / 2, expirePeriod / 2, TimeUnit.MILLISECONDS);

    }

    private String toServicePath(URL url) {
        return root + url.getParameter(Constants.NAME);
    }

    private String toCategoryPath(URL url) {
        return toServicePath(url) + Constants.PATH_SEPARATOR + url.getParameter(Constants.CATEGORY, Constants.PROVIDERS);
    }

    @Override
    public void register(URL url) {
        super.register(url);
        String key = toCategoryPath(url);
        String value = url.urlString();
        String expire = String.valueOf(System.currentTimeMillis() + expirePeriod);
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    jedis.hset(key, value, expire);
                    jedis.publish(key, Constants.REGISTER);
                } finally {
                    jedis.close();
                }
            } catch (Throwable t) {
                logger.info(String.format("failed to register service .registry: %s, service: %s, cause: %s", entry.getKey(), url, t.getMessage()), t);
            }
        }
    }

    @Override
    public void unregister(URL url) {
        super.unregister(url);
        String key = toCategoryPath(url);
        String value = url.toString();
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    jedis.hdel(key, value);
                    jedis.publish(key, Constants.UNREGISTER);
                } finally {
                    jedis.close();
                }
            } catch (Throwable t) {
                logger.info(String.format("failed to unregister service .registry: %s, service: %s, cause: %s", entry.getKey(), url, t.getMessage()), t);
            }
        }
    }

    @Override
    public void subscribe(URL url, NotifyListener listener) {
        super.subscribe(url, listener);
        String service = toServicePath(url);
        //获取监听线程
        Notifier notifier = notifiers.get(service);
        if (Objects.isNull(notifier)) {
            //监听线程为空则新建
            Notifier newNotifier = new Notifier(service);
            notifiers.putIfAbsent(service, newNotifier);
            notifier = notifiers.get(service);
            if (Objects.equals(notifier, newNotifier)) {
                notifier.start();
            }
        }
        for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
            JedisPool jedisPool = entry.getValue();
            try {
                Jedis jedis = jedisPool.getResource();
                try {
                    //获取当前service对应的所以keys
                    Set<String> keys = jedis.keys(service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE);
                    doNotify(jedis, keys, url, Collections.singletonList(listener));
                    break;
                } finally {
                    jedis.close();
                }
            } catch (Throwable t) {
                logger.info(String.format("failed to subscribe service .registry: %s, service: %s, cause: %s", entry.getKey(), url, t.getMessage()), t);
            }
        }
    }

    @Override
    public void unsubscribe(URL url, NotifyListener listener) {
        super.unsubscribe(url, listener);
    }

    private void doNotify(Jedis jedis, String key) {
        for (Map.Entry<URL, Set<NotifyListener>> entry : new HashMap<>(getSubscribed()).entrySet()) {
            doNotify(jedis, Collections.singletonList(key), entry.getKey(), new HashSet<>(entry.getValue()));
        }
    }

    private void doNotify(Jedis jedis, Collection<String> keys, URL url, Collection<NotifyListener> listeners) {
        if (ObjectUtil.isEmpty(keys) || ObjectUtil.isEmpty(listeners)) {
            return;
        }
        long now = System.currentTimeMillis();
        List<URL> result = new ArrayList<>();
        for (String key : keys) {
            List<URL> urls = new ArrayList<>();
            //获取当前key对应的所有field
            Map<String, String> values = jedis.hgetAll(key);
            if (ObjectUtil.nonEmpty(values)) {
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    //获取field对应的值
                    URL u = URL.valueOf(entry.getKey());
                    //如果是为监听的，则监听
                    if (Long.parseLong(entry.getValue()) >= now) {
                        if (AbstractRegistry.isMatch(url, u)) {
                            urls.add(u);
                        }
                    }
                }
            }
            result.addAll(urls);
            logger.info("redis notify: {} = {}", key, urls);
        }
        if (ObjectUtil.isEmpty(result)) {
            return;
        }
        for (NotifyListener listener : listeners) {
            notify(url, listener, result);
        }
    }

    private class Notifier extends Thread {

        private final String service;
        private volatile Jedis jedis;

        public Notifier(String service) {
            super.setDaemon(true);
            super.setName("TeezRedisSubscribe");
            this.service = service;
        }

        @Override
        public void run() {
            try {
                for (Map.Entry<String, JedisPool> entry : jedisPools.entrySet()) {
                    JedisPool jedisPool = entry.getValue();
                    try {
                        jedis = jedisPool.getResource();
                        try {
                            doNotify(jedis, service);
                            jedis.psubscribe(new NotifySub(jedisPool), service + Constants.PATH_SEPARATOR + Constants.ANY_VALUE);
                            break;
                        } finally {
                            jedis.close();
                        }
                    } catch (Throwable t) {
                        logger.info(String.format("failed to subscribe. registry: %S , cause: %s", entry.getKey(), t.getMessage()), t);
                    }
                }
            } catch (Throwable t) {
                logger.info(t.getMessage(), t);
            }
        }

        public void shutdown() {
            try {
                jedis.disconnect();
            } catch (Throwable t) {
                logger.info(t.getMessage(), t);
            }
        }
    }

    private class NotifySub extends JedisPubSub {
        private final JedisPool jedisPool;

        public NotifySub(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }

        @Override
        public void onMessage(String key, String msg) {
            if (msg.equals(Constants.REGISTER)
                    || msg.equals(Constants.UNREGISTER)) {
                try {
                    Jedis jedis = jedisPool.getResource();
                    try {
                        doNotify(jedis, key);
                    } finally {
                        jedis.close();
                    }
                } catch (Throwable t) {
                    logger.info(t.getMessage(), t);
                }
            }
        }

        @Override
        public void onPMessage(String pattern, String key, String msg) {
            onMessage(key, msg);
        }
    }
}