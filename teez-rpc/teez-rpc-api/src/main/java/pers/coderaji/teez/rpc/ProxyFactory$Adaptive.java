package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;

/**
 * @author aji
 * @date 2024/8/1 23:06
 * @description 代理工厂适配器
 */
@Adaptive
public class ProxyFactory$Adaptive implements ProxyFactory{
    @Override
    public <T> T getProxy(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return null;
    }
}
