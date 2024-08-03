package pers.coderaji.teez.cluster.support;

import pers.coderaji.teez.cluster.Directory;
import pers.coderaji.teez.cluster.LoadBalance;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Result;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 16:12
 * @description 故障转移群集调用器
 */
public class FailoverClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public FailoverClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        return null;
    }
}
