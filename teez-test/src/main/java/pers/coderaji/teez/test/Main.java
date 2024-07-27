package pers.coderaji.teez.test;

import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.ServiceConfig;
import pers.coderaji.teez.config.annotation.Service;
import pers.coderaji.teez.test.api.TestApi;
import pers.coderaji.teez.test.api.impl.TestApiIml;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        TestApi testApi = new TestApiIml();
        Class<? extends TestApi> testApiClass = testApi.getClass();
        Service annotation = testApiClass.getAnnotation(Service.class);
        ServiceConfig<TestApi> serviceConfig = new ServiceConfig<>(annotation);

        logger.info("serviceConfig：{}",serviceConfig);
    }
}