package pers.coderaji.teez.test.api.impl;

import pers.coderaji.teez.config.annotation.Service;
import pers.coderaji.teez.test.api.TestApi;

/**
 * @author aji
 * @date 2024/7/28 0:43
 * @description TODO
 */
@Service(type = TestApi.class, name = "pers.coderaji.teez.test.api.TestApi", group = "test", version = "1.0")
public class TestApiIml implements TestApi {
}
