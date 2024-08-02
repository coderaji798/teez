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
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("test123");
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setHost("127.0.0.1");
        registryConfig.setPort(6379);
        registryConfig.setProtocol("redis");
        Map<String, String> redisParam = new HashMap<>();
        redisParam.put("max.idle", String.valueOf(20));
        redisParam.put("min.idle", String.valueOf(10));
        redisParam.put("max.active", String.valueOf(20));
        redisParam.put("max.total", String.valueOf(30));
        registryConfig.setParameters(redisParam);
        applicationConfig.setRegistryConfigs(Collections.singletonList(registryConfig));
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setProtocol("teez");
        protocolConfig.setPort(20880);
        protocolConfig.setHost("127.0.0.1");
        applicationConfig.setProtocolConfig(protocolConfig);

        TestApi testApi = new TestApiIml();
        Class<? extends TestApi> testApiClass = testApi.getClass();
        Service annotation = testApiClass.getAnnotation(Service.class);
        Method[] methods = testApiClass.getDeclaredMethods();
        List<MethodConfig> methodConfigs = new ArrayList<>();
        for (Method method : methods) {
            MethodConfig methodConfig = new MethodConfig();
            methodConfig.setName(method.getName());
            methodConfig.setCallback(method.getReturnType().getName());
            Class<?>[] parameterTypes = method.getParameterTypes();
            List<ArgumentConfig> argumentConfigs = Stream.of(parameterTypes).map(type -> new ArgumentConfig(type.getName())).collect(Collectors.toList());
            methodConfig.setArguments(argumentConfigs);
            methodConfigs.add(methodConfig);
        }
        ServiceConfig<TestApi> serviceConfig = new ServiceConfig<>(annotation);
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
