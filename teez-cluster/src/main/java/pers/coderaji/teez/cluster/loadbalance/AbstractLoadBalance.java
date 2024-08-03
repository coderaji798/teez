package pers.coderaji.teez.cluster.loadbalance;

import pers.coderaji.teez.cluster.LoadBalance;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/3 16:17
 * @description 抽象负载平衡
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {
        if (ObjectUtil.isEmpty(invokers)) {
            return null;
        }
        if (Objects.equals(invokers.size(), 0)) {
            return invokers.get(0);
        }
        return doSelect(invokers, url, invocation);
    }

    protected abstract <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation);
}
