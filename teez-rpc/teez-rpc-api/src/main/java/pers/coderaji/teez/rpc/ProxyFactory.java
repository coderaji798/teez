package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/1 22:53
 * @description 代理工厂
 */
@SPI("jdk")
public interface ProxyFactory {
    /**
     * 获取代理
     * @param invoker
     * @return
     * @param <T>
     * @throws RpcException
     */
    <T> T getProxy(Invoker<T> invoker) throws RpcException;

    /**
     * 创建调用程序
     *
     * @param <T>
     * @param proxy
     * @param type
     * @param url
     * @return invoker
     */

    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException;
}
