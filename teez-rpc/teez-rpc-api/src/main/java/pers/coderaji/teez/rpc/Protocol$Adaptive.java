package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;

/**
 * @author aji
 * @date 2024/8/1 23:30
 * @description 协议适配器
 */
@Adaptive
public class Protocol$Adaptive implements Protocol{
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        return null;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }
}
