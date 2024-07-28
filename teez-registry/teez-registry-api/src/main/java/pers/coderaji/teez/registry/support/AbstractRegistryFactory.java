package pers.coderaji.teez.registry.support;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.registry.RegistryFactory;

import java.util.Map;
import java.util.Objects;
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
        Registry registry = createRegistry(url);
        return null;
    }

    protected abstract Registry createRegistry(URL url);
}
