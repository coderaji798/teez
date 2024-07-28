package pers.coderaji.teez.registry;

import pers.coderaji.teez.common.URL;

import java.util.List;

/**
 * @author aji
 * @date 2024/7/28 10:53
 * @description 通知侦听器
 */
public interface NotifyListener {

    void notify(List<URL> urls);

}
