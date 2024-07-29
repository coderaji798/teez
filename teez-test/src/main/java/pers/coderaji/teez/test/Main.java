package pers.coderaji.teez.test;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.AbstractConfig;
import pers.coderaji.teez.config.ServiceConfig;
import pers.coderaji.teez.config.annotation.Service;
import pers.coderaji.teez.registry.redis.RedisRegistry;
import pers.coderaji.teez.registry.redis.RedisRegistryFactory;
import pers.coderaji.teez.test.api.TestApi;
import pers.coderaji.teez.test.api.impl.TestApiIml;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        TestApi testApi = new TestApiIml();
        Class<? extends TestApi> testApiClass = testApi.getClass();
        Service annotation = testApiClass.getAnnotation(Service.class);
        ServiceConfig<TestApi> serviceConfig = new ServiceConfig<>(annotation);

        logger.info("serviceConfig：{}", serviceConfig);
        Map<String, String> map = new HashMap<>();
        //AbstractConfig.appendParameters(map, serviceConfig, null);
        map.put("protocol", "empty");
        URL url = URL.valueOf(map);
        logger.info("url{}",url.urlString());



        map.put("max.idle", "20");
        map.put("min.idle", "10");
        map.put("max.active", "20");
        map.put("max.total", "30");
        map.put("protocol", "empty");
        logger.info("map{}",map);
        URL registryUrl = new URL("redis", null, null, "127.0.0.1", 6379, null, map);
        logger.info("registryUrl{}",registryUrl.urlString());
        RedisRegistry redisRegistry = new RedisRegistry(registryUrl);
        redisRegistry.register(url);

        try {
            //Thread.sleep(60 * 1000);
        } catch (Exception ignore) {

        }
    }
}