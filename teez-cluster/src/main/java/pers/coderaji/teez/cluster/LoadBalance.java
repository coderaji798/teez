package pers.coderaji.teez.cluster;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 15:32
 * @description 负载均衡
 */
@SPI("random")
public interface LoadBalance {
    <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;
}
