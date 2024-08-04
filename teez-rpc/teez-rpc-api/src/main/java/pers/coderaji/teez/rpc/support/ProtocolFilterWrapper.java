package pers.coderaji.teez.rpc.support;

import lombok.Getter;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author aji
 * @date 2024/8/4 20:11
 * @description 协议包装器
 */
@Getter
public class ProtocolFilterWrapper implements Protocol {

    private final Protocol protocol;

    public ProtocolFilterWrapper(Protocol protocol) {
        Assert.nonNull(protocol, "protocol is null");
        this.protocol = protocol;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        if (Constants.REGISTRY.equals(invoker.getUrl().getProtocol())) {
            return protocol.export(invoker);
        }
        return protocol.export(buildInvokerChain(invoker));
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        if (Constants.REGISTRY.equals(url.getProtocol())) {
            return protocol.refer(type, url);
        }
        return buildInvokerChain(protocol.refer(type, url));
    }

    private static <T> Invoker<T> buildInvokerChain(final Invoker<T> invoker) {
        Invoker<T> last = invoker;
        ExtensionLoader<Filter> loader = ExtensionLoader.getExtensionLoader(Filter.class);
        Set<String> set = loader.getSupportedExtensions();
        List<Filter> filters = new ArrayList<>();
        for (String string : set) {
            filters.add(loader.getExtension(string));
        }
        if (!filters.isEmpty()) {
            for (int i = filters.size() - 1; i >= 0; i--) {
                final Filter filter = filters.get(i);
                final Invoker<T> next = last;
                last = new Invoker<T>() {

                    @Override
                    public Class<T> getInterface() {
                        return invoker.getInterface();
                    }

                    @Override
                    public URL getUrl() {
                        return invoker.getUrl();
                    }

                    @Override
                    public Result invoke(Invocation invocation) throws RpcException {
                        return filter.invoke(next, invocation);
                    }

                    @Override
                    public String toString() {
                        return invoker.toString();
                    }
                };
            }
        }
        return last;
    }
}
