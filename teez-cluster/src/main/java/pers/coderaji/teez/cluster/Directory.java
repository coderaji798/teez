package pers.coderaji.teez.cluster;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 15:25
 * @description 目录
 */
public interface Directory<T> {

    URL getUrl();

    Class<T> getInterface();

    List<Invoker<T>> list(Invocation invocation) throws RpcException;
}
