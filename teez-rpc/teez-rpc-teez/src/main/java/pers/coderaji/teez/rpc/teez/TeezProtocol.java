package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.remoting.*;
import pers.coderaji.teez.remoting.support.AbstractClientHandler;
import pers.coderaji.teez.rpc.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aji
 * @date 2024/8/3 18:50
 * @description Teez协议
 */
public class TeezProtocol implements Protocol {

    private static final Logger logger = Logger.getLogger(TeezProtocol.class);

    private static final Transporter transporter = ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();

    private final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<>();

    private final Map<String, Server> serverMap = new ConcurrentHashMap<>();

    private ClientHandler requestHandler = new AbstractClientHandler() {

        @Override
        public Object reply(Client channel, Object msg) throws RemotingException {
            Assert.nonFalse(msg instanceof Invocation, "msg is not Invocation");
            Invocation invocation = (Invocation) msg;
            Invoker<?> invoker = invocation.getInvoker();
            Assert.nonNull(invoker, "invoker is null");
            URL invokerUrl = invoker.getUrl();
            Assert.nonNull(invokerUrl, "invokerUrl is null");
            String key = invokerUrl.getParameter(Constants.REFERENCE_NAME);
            TeezExporter<?> teezExporter = (TeezExporter<?>) exporterMap.get(key);
            Assert.nonNull(teezExporter, "exporter is null");
            Invoker<?> exporterInvoker = teezExporter.getInvoker();
            Assert.nonNull(exporterInvoker, "exporterInvoker is null");
            URL exporterInvokerUrl = exporterInvoker.getUrl();
            Assert.nonNull(exporterInvokerUrl, "exporterInvokerUrl is null");
            String methodKey = Constants.METHODS + Constants.DOT + invocation.getMethodName();
            String parameter = exporterInvokerUrl.getParameter(methodKey);
            Assert.nonEmpty(parameter, String.format("%s not exists", parameter));
            return exporterInvoker.invoke(invocation);
        }


        @Override
        public void sent(Client client, Object message) throws RemotingException {
            //ignore
        }

        @Override
        public void received(Client client, Object message) throws RemotingException {
            if (message instanceof Invocation) {
                reply(client, message);
            }
        }


        @Override
        public ClientHandler getHandler() {
            return this;
        }
    };

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        Assert.nonNull(invoker, "invoker is null");
        URL invokerUrl = invoker.getUrl();
        Assert.nonNull(invokerUrl, "invokerUrl is null");
        String key = invokerUrl.getParameter(Constants.SERVICE_NAME);
        TeezExporter<T> exporter = new TeezExporter<>(invoker, key, exporterMap);
        exporterMap.put(key, exporter);
        //创建server
        Map<String, String> map = invokerUrl.getParameters(Constants.PROVIDER);
        URL url = URL.valueOf(map);
        String address = url.getAddress();
        Server server = serverMap.get(address);
        if (Objects.isNull(server)) {
            server = transporter.bind(url, requestHandler);
            serverMap.put(address, server);
        }
        return exporter;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        return null;
    }
}
