package pers.coderaji.teez.config.invoker;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.config.ServiceConfig;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Result;
import pers.coderaji.teez.rpc.RpcException;

/**
 * @author aji
 * @date 2024/8/1 23:10
 * @description 提供者元数据调用器
 */
public class ProviderMetaDataInvoker<T> implements Invoker {
    private final Invoker<T> invoker;

    private ServiceConfig meta;

    public ProviderMetaDataInvoker(Invoker<T> invoker, ServiceConfig meta) {
        this.invoker = invoker;
        this.meta = meta;
    }

    @Override
    public URL getUrl() {
        return invoker.getUrl();
    }

    @Override
    public Class getInterface() {
        return invoker.getInterface();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }
}
