package pers.coderaji.teez.cluster;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.extension.SPI;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 15:32
 * @description 负载均衡适配器
 */
@Adaptive
public class LoadBalance$Adaptive implements LoadBalance {
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        Assert.nonEmpty(invokers, "invokers is empty");
        Assert.nonNull(url, "url is null");
        Assert.nonNull(invocation, "invocation is null");
        LoadBalance loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(url.getProtocol());
        return loadBalance.select(invokers, url, invocation);
    }
}
