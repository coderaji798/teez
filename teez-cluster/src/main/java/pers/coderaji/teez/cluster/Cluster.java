package pers.coderaji.teez.cluster;

import pers.coderaji.teez.common.extension.SPI;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

/**
 * @author aji
 * @date 2024/8/3 15:09
 * @description 集群
 */
@SPI("failover")
public interface Cluster {

    <T> Invoker<T> join(Directory<T> directory) throws RpcException;

}
