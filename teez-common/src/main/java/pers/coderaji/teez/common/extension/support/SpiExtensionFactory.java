package pers.coderaji.teez.common.extension.support;

import pers.coderaji.teez.common.extension.ExtensionFactory;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.extension.SPI;

import java.util.Objects;

/**
 * @author aji
 * @description Spi扩建厂
 * @date 2024/7/25 10:53
 */
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        if (Objects.nonNull(type) && type.isInterface() && type.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> loader = ExtensionLoader.getExtensionLoader(type);
            if (Objects.nonNull(loader)) {
                return loader.getAdaptiveExtension();
            }
        }
        return null;
    }
}
