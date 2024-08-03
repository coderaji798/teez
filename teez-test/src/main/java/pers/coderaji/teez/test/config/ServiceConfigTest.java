package pers.coderaji.teez.test.config;

import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.*;
import pers.coderaji.teez.config.annotation.Service;
import pers.coderaji.teez.test.api.TestApi;
import pers.coderaji.teez.test.api.impl.TestApiIml;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author aji
 * @date 2024/8/1 22:19
 * @description TODO
 */
public class ServiceConfigTest {
    private static final Logger logger = Logger.getLogger(ServiceConfigTest.class);

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = Factory.getApplicationConfig();

        TestApi testApi = new TestApiIml();
        Class<? extends TestApi> testApiClass = testApi.getClass();
        Service annotation = testApiClass.getAnnotation(Service.class);
        List<MethodConfig> methodConfigs = Factory.getMethodConfigs(testApiClass);
        ServiceConfig serviceConfig = new ServiceConfig<>(annotation);
        serviceConfig.setProvider(applicationConfig);
        serviceConfig.setMethods(methodConfigs);
        serviceConfig.setReference(testApi);

        logger.info("serviceConfig:{}", serviceConfig);
        serviceConfig.export();

        try {
            Thread.sleep(60 * 1000);
        } catch (Exception ignore) {

        }
    }
}
