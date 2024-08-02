package pers.coderaji.teez.registry.support;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.registry.RegistryFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aji
 * @date 2024/7/28 11:24
 * @description 抽象注册表工厂
 */

public abstract class AbstractRegistryFactory implements RegistryFactory {

    private static final Map<String, Registry> REGISTRIES = new ConcurrentHashMap<>();

    @Override
    public Registry getRegistry(URL url) {
        Assert.nonNull(url, "url is null");
        String key = url.urlString();
        Registry registry = REGISTRIES.get(key);
        if (registry != null) {
            return registry;
        }
        registry = createRegistry(url);
        Assert.nonNull(registry, "registry is null");
        REGISTRIES.put(key, registry);
        return registry;
    }

    protected abstract Registry createRegistry(URL url);
}
