package pers.coderaji.teez.registry.support;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.registry.RegistryFactory;

import java.util.Objects;

/**
 * @author aji
 * @date 2024/7/28 11:36
 * @description 自适应注册表工厂
 */
@Adaptive
public class RegistryFactory$Adaptive implements RegistryFactory {
    @Override
    public Registry getRegistry(URL url) {
        if (Objects.isNull(url) || Objects.isNull(url.getProtocol()) || url.getParameters().isEmpty()) {
            throw new IllegalArgumentException("url is null");
        }
        RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getExtension(url.getProtocol());
        return registryFactory.getRegistry(url);
    }
}
