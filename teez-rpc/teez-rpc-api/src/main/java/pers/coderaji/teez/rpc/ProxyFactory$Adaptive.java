package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;

/**
 * @author aji
 * @date 2024/8/1 23:06
 * @description 代理工厂适配器
 */
@Adaptive
public class ProxyFactory$Adaptive implements ProxyFactory {

    private static final Logger logger = Logger.getLogger(ProxyFactory$Adaptive.class);

    @Override
    public <T> T getProxy(Invoker<T> invoker) throws RpcException {
        Assert.nonNull(invoker, "invoker is null");
        Assert.nonNull(invoker.getUrl(), "url is null");
        URL url = invoker.getUrl();
        String name = url.getParameter(Constants.PROXY, "jdk");
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(name);
        return proxyFactory.getProxy(invoker);
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) throws RpcException {
        Assert.nonNull(url, "url is null");
        String name = url.getParameter(Constants.PROXY, "jdk");
        ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getExtension(name);
        return proxyFactory.getInvoker(proxy, type, url);
    }
}
