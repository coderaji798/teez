package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.URL;

/**
 * @author aji
 * @date 2024/8/1 22:58
 * @description 调用程序
 */
public interface Invoker<T> {
    URL getUrl();

    Class<T> getInterface();

    Result invoke(Invocation invocation) throws RpcException;
}
