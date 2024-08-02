package pers.coderaji.teez.rpc.proxy;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.*;

import java.lang.reflect.Method;

/**
 * @author aji
 * @date 2024/8/3 1:20
 * @description 代理调用器
 */
public class ProxyInvoker<T> implements Invoker<T> {
    private final T proxy;

    private final Class<T> type;

    private final URL url;

    public ProxyInvoker(T proxy, Class<T> type, URL url) {
        Assert.nonNull(proxy, "proxy is null");
        Assert.nonNull(type, "type is null");
        Assert.nonNull(url, "url is null");
        this.proxy = proxy;
        this.type = type;
        this.url = url;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        Assert.nonNull(invocation, "invocation is null");
        try {
            Method method = proxy.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Object result = method.invoke(proxy, invocation.getArguments());
            return new RpcResult(result);
        } catch (Exception e) {
            return new RpcResult(e);
        }
    }
}
