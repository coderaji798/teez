package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/4 20:22
 * @description 过滤器
 */
@SPI
public interface Filter {
    Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException;
}
