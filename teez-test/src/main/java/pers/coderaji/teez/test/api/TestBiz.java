package pers.coderaji.teez.test.api;

import pers.coderaji.teez.config.annotation.Reference;

/**
 * @author aji
 * @date 2024/8/3 13:10
 * @description TODO
 */
public class TestBiz {
    @Reference(type = TestApi.class, name = "pers.coderaji.teez.test.api.TestApi", group = "test", version = "1.0")
    private TestApi testApi;
}
