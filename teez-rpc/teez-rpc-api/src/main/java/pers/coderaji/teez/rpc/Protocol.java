package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/1 23:26
 * @description 协议
 */
@SPI(Constants.TEEZ)
public interface Protocol {
    /**
     * 暴露
     *
     * @param invoker
     * @param <T>
     * @return
     * @throws RpcException
     */
    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;

    /**
     * 引用
     *
     * @param type
     * @param url
     * @param <T>
     * @return
     * @throws RpcException
     */
    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;
}
