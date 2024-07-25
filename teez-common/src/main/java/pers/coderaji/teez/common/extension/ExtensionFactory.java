package pers.coderaji.teez.common.extension;

/**
 * @author aji
 * @description 扩建工厂
 * @date 2024/7/24 15:04
 */
@SPI("spi")
public interface ExtensionFactory {
    /**
     * 获取扩展类
     * @param type
     * @param name
     * @return
     * @param <T>
     */
    <T> T getExtension(Class<T> type, String name);
}
