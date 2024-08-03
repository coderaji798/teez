package pers.coderaji.teez.cluster.support;

import pers.coderaji.teez.cluster.Cluster;
import pers.coderaji.teez.cluster.Directory;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

/**
 * @author aji
 * @date 2024/8/3 15:59
 * @description 故障转移群集
 */
public class FailoverCluster implements Cluster {
    @Override
    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        return new FailoverClusterInvoker<>(directory);
    }
}
