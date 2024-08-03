package pers.coderaji.teez.cluster.support;

import pers.coderaji.teez.cluster.Directory;
import pers.coderaji.teez.cluster.LoadBalance;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Result;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 16:02
 * @description 抽象集群调用器
 */
public abstract class AbstractClusterInvoker<T> implements Invoker<T> {
    protected final Directory<T> directory;

    private volatile Invoker<T> invoker = null;

    public AbstractClusterInvoker(Directory<T> directory) {
        this.directory = directory;
    }

    @Override
    public URL getUrl() {
        return directory.getUrl();
    }

    @Override
    public Class<T> getInterface() {
        return directory.getInterface();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        List<Invoker<T>> invokers = directory.list(invocation);
        LoadBalance loadBalance = null;
        if (ObjectUtil.nonEmpty(invokers)) {
            URL url = invokers.get(0).getUrl();
            loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(url.getProtocol());
        }
        return doInvoke(invocation, invokers, loadBalance);
    }

    protected abstract Result doInvoke(Invocation invocation, List<Invoker<T>> invokers,
                                       LoadBalance loadbalance) throws RpcException;
}
