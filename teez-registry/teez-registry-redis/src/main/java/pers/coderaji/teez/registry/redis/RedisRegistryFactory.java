package pers.coderaji.teez.registry.redis;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.registry.support.AbstractRegistryFactory;

/**
 * @author aji
 * @date 2024/7/28 11:27
 * @description Redis注册表工厂
 */
public class RedisRegistryFactory extends AbstractRegistryFactory {

    @Override
    protected Registry createRegistry(URL url) {
        return new RedisRegistry(url);
    }
}
