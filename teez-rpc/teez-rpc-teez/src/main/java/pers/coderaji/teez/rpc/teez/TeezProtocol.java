package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.rpc.Exporter;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Protocol;
import pers.coderaji.teez.rpc.RpcException;

/**
 * @author aji
 * @date 2024/8/3 18:50
 * @description Teez协议
 */
public class TeezProtocol implements Protocol {
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }
}
