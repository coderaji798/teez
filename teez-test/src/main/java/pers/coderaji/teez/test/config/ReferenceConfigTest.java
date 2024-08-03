package pers.coderaji.teez.test.config;

import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.ApplicationConfig;
import pers.coderaji.teez.config.MethodConfig;
import pers.coderaji.teez.config.ReferenceConfig;
import pers.coderaji.teez.config.RegistryConfig;
import pers.coderaji.teez.config.annotation.Reference;
import pers.coderaji.teez.test.api.TestApi;
import pers.coderaji.teez.test.api.TestBiz;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 13:05
 * @description TODO
 */
public class ReferenceConfigTest {
    private static final Logger logger = Logger.getLogger(ReferenceConfigTest.class);

    public static void main(String[] args) throws Exception {
        ApplicationConfig applicationConfig = Factory.getApplicationConfig();
        TestBiz testBiz = new TestBiz();
        Field field = testBiz.getClass().getDeclaredField("testApi");
        Reference reference = field.getAnnotation(Reference.class);
        ReferenceConfig registryConfig = new ReferenceConfig<>(reference);
        registryConfig.setConsumer(applicationConfig);
        List<MethodConfig> methodConfigs = Factory.getMethodConfigs(field.getType());
        registryConfig.setMethods(methodConfigs);

        logger.info("registryConfig:{}", registryConfig);
        registryConfig.get();


    }
}
