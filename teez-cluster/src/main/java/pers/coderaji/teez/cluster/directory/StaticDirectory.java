package pers.coderaji.teez.cluster.directory;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 16:29
 * @description 静态目录
 */
public class StaticDirectory<T> extends AbstractDirectory<T> {

    private final List<Invoker<T>> invokers;

    public StaticDirectory(List<Invoker<T>> invokers) {
        this(null, invokers);
    }

    public StaticDirectory(URL directoryUrl, List<Invoker<T>> invokers) {
        super(directoryUrl);
        Assert.nonEmpty(invokers, "invokers is empty");
        this.invokers = invokers;
    }

    @Override
    public Class<T> getInterface() {
        return invokers.get(0).getInterface();
    }

    @Override
    public List<Invoker<T>> list(Invocation invocation) throws RpcException {
        return invokers;
    }
}
