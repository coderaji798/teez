package pers.coderaji.teez.registry;

import pers.coderaji.teez.common.URL;

import java.util.List;

/**
 * @author aji
 * @date 2024/7/28 10:37
 * @description 注册
 */
public interface Registry {

    URL getUrl();

    void destroy();

    void register(URL url);

    void unregister(URL url);

    void subscribe(URL url, NotifyListener listener);

    void unsubscribe(URL url, NotifyListener listener);
}
