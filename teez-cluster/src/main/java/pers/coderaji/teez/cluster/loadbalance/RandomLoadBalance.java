package pers.coderaji.teez.cluster.loadbalance;

import pers.coderaji.teez.cluster.LoadBalance;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;
import java.util.Random;

/**
 * @author aji
 * @date 2024/8/3 16:14
 * @description 随机负载平衡
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    private final Random random = new Random();

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        int size = invokers.size();
        return invokers.get(random.nextInt(size));
    }
}
