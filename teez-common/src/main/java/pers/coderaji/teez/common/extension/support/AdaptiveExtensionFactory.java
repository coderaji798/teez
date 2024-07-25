package pers.coderaji.teez.common.extension.support;

import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionFactory;
import pers.coderaji.teez.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author aji
 * @description 自适应扩展工厂
 * @date 2024/7/25 10:36
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {
        //1.加载所有扩展
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<>();
        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }
        //2.存放在不可修改集合里
        this.factories = Collections.unmodifiableList(list);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        //1.遍历扩展工厂，查找扩展
        for (ExtensionFactory factory : factories) {
            //2.找打扩展直接返回
            T extension = factory.getExtension(type, name);
            if (Objects.nonNull(extension)) {
                return extension;
            }
        }
        return null;
    }
}