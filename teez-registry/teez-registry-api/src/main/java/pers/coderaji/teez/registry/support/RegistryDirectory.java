package pers.coderaji.teez.registry.support;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.registry.NotifyListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/7/28 11:05
 * @description 注册表目录
 */
public class RegistryDirectory<T> implements NotifyListener {
    private final URL url;
    private final Class<T> type;
    private final String[] methods;
    private final Map<String, String> paramters;

    public RegistryDirectory(URL url, Class<T> type) {
        if (Objects.isNull(url) || Objects.isNull(type)) {
            throw new IllegalArgumentException("argument is null");
        }
        this.url = url;
        this.type = type;
        String string = url.getParameters().get(Constants.METHODS);
        this.methods = Objects.isNull(string) ? null : Constants.COMMA_SPLIT_PATTERN.split(string);
        this.paramters = new HashMap<>(url.getParameters());
    }

    @Override
    public void notify(List<URL> urls) {

    }
}
