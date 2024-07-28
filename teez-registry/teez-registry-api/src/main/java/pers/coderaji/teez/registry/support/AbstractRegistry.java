package pers.coderaji.teez.registry.support;

import lombok.Data;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.registry.NotifyListener;
import pers.coderaji.teez.registry.Registry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author aji
 * @date 2024/7/28 10:56
 * @description 抽象注册表
 */
@Data
public abstract class AbstractRegistry implements Registry {

    private static final Logger logger = Logger.getLogger(AbstractRegistry.class);

    private final Set<URL> registered = Collections.synchronizedSet(new HashSet<>());

    private final ConcurrentMap<URL, Set<NotifyListener>> subscribed = new ConcurrentHashMap<>();

    private final ConcurrentMap<URL, Map<String, List<URL>>> notified = new ConcurrentHashMap<>();

    private URL registryUrl;

    public AbstractRegistry(URL registryUrl) {
        if (registryUrl == null) {
            throw new IllegalArgumentException("registry url is null");
        }
        this.registryUrl = registryUrl;
    }

    @Override
    public URL getUrl() {
        return registryUrl;
    }

    @Override
    public void destroy() {
        Set<URL> set = new HashSet<>(registered);
        if (!set.isEmpty()) {
            for (URL url : set) {
                unregister(url);
                logger.info("destroy registered url: {}", url);
            }
        }
        Map<URL, Set<NotifyListener>> map = new HashMap<>(subscribed);
        if (!map.isEmpty()) {
            map.forEach((url, v) -> {
                v.forEach(item -> {
                    unsubscribe(url, item);
                    logger.info("destroy subscribed url: {}", url);
                });
            });
        }
    }

    @Override
    public void register(URL url) {
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("register url is null");
        }
        registered.add(url);
    }

    @Override
    public void unregister(URL url) {
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("unregister url is null");
        }
        registered.remove(url);
    }

    @Override
    public void subscribe(URL url, NotifyListener listener) {
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("subscribe url is null");
        }
        if (Objects.isNull(listener)) {
            throw new IllegalArgumentException("subscribe listener is null");
        }
        Set<NotifyListener> listeners = subscribed.get(url);
        if (listeners == null) {
            subscribed.putIfAbsent(url, Collections.synchronizedSet(new HashSet<>()));
            listeners = subscribed.get(url);
        }
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(URL url, NotifyListener listener) {
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("unsubscribe url is null");
        }
        if (Objects.isNull(listener)) {
            throw new IllegalArgumentException("unsubscribe listener is null");
        }
        Set<NotifyListener> listeners = subscribed.get(url);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    protected void notify(URL url, NotifyListener listener, List<URL> urls) {
        if (Objects.isNull(url)) {
            throw new IllegalArgumentException("notify url is null");
        }
        if (Objects.isNull(listener)) {
            throw new IllegalArgumentException("notify listener is null");
        }
        if (Objects.isNull(urls) || urls.isEmpty()) {
            logger.info("notify urls is empty");
            return;
        }
        try {
            Map<String, List<URL>> result = new HashMap<>();
            for (URL u : urls) {
                if (AbstractRegistry.isMatch(url, u)) {
                    String category = u.getParameter(Constants.CATEGORY, Constants.PROVIDERS);
                    List<URL> categoryList = result.computeIfAbsent(category, k -> new ArrayList<>());
                    categoryList.add(u);
                }
            }
            if (result.isEmpty()) {
                return;
            }
            Map<String, List<URL>> categoryNotified = notified.get(url);
            if (Objects.isNull(categoryNotified)) {
                notified.putIfAbsent(url, new ConcurrentHashMap<>());
                categoryNotified = notified.get(url);
            }
            for (Map.Entry<String, List<URL>> entry : result.entrySet()) {
                String category = entry.getKey();
                List<URL> categoryList = entry.getValue();
                categoryNotified.put(category, categoryList);
                listener.notify(categoryList);
            }
        } catch (Exception t) {
            logger.info(String.format("failed to notify for subscribe %s, cause: %s", url, t.getMessage()), t);
        }
    }

    public static boolean isMatch(URL consumerUrl, URL providerUrl) {
        String consumerInterface = consumerUrl.getParameter(Constants.TYPE);
        String providerInterface = providerUrl.getParameter(Constants.TYPE);
        if (!Objects.equals(consumerInterface, providerInterface)) {
            return false;
        }
        String consumerGroup = consumerUrl.getParameter(Constants.GROUP);
        String consumerVersion = consumerUrl.getParameter(Constants.VERSION);

        String providerGroup = providerUrl.getParameter(Constants.GROUP);
        String providerVersion = providerUrl.getParameter(Constants.VERSION);
        return (Constants.ANY_VALUE.equals(consumerGroup) || Objects.equals(consumerGroup, providerGroup))
                && (Constants.ANY_VALUE.equals(consumerVersion) || Objects.equals(consumerVersion, providerVersion));
    }
}
