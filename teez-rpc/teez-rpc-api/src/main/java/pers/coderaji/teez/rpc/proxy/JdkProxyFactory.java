package pers.coderaji.teez.rpc.proxy;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.ProxyFactory;
import pers.coderaji.teez.rpc.RpcException;

import java.lang.reflect.Proxy;

/**
 * @author aji
 * @date 2024/8/3 1:04
 * @description jdk代理工程
 */
public class JdkProxyFactory implements ProxyFactory {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Invoker<T> invoker) throws RpcException {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{invoker.getInterface()}, new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        return new ProxyInvoker<>(proxy, type, url);
    }
}
