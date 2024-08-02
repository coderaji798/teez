package pers.coderaji.teez.rpc;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;

/**
 * @author aji
 * @date 2024/8/1 23:30
 * @description 协议适配器
 */
@Adaptive
public class Protocol$Adaptive implements Protocol {
    private static final Logger logger = Logger.getLogger(Protocol$Adaptive.class);
    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        Assert.nonNull(invoker, "invoker is null");
        Assert.nonNull(invoker.getUrl(), "url is null");
        Assert.nonEmpty(invoker.getUrl().getProtocol(), "url protocol is null");
        URL url = invoker.getUrl();
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(url.getProtocol());
        return protocol.export(invoker);
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        Assert.nonNull(type, "type is null");
        Assert.nonNull(url, "url is null");
        Assert.nonEmpty(url.getProtocol(), "url protocol is null");
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(url.getProtocol());
        return protocol.refer(type, url);
    }
}
