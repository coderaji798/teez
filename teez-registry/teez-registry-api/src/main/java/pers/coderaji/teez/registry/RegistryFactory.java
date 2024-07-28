package pers.coderaji.teez.registry;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/7/28 10:37
 * @description 注册工厂
 */

@SPI("redis")
public interface RegistryFactory {

    Registry getRegistry(URL url);
}