package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.common.utl.NetUtil;
import pers.coderaji.teez.remoting.*;

import java.net.InetSocketAddress;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/4 11:58
 * @description 抽象客户端
 */
public abstract class AbstractClient implements Client {

    private static final Logger logger = Logger.getLogger(AbstractClient.class);

    private final ClientHandler handler;

    private volatile URL url;

    private Codec codec;

    public AbstractClient(ClientHandler handler, URL url) {
        Assert.nonNull(handler, "handler is null");
        Assert.nonNull(url, "url is null");
        this.handler = handler;
        this.url = url;
        String codecName = url.getParameter(Constants.CODEC, Constants.TEEZ);
        this.codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(codecName);
        try {
            doOpen();
        } catch (Throwable e) {
            logger.info(e);
        }
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public ClientHandler getClientHandler() {
        return handler;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        Client Client = getClient();
        if (Objects.isNull(Client)) {
            return getUrl().toInetSocketAddress();
        }
        return Client.getRemoteAddress();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        Client Client = getClient();
        if (Objects.isNull(Client)) {
            return InetSocketAddress.createUnresolved(NetUtil.getLocalHost(), 0);
        }
        return Client.getLocalAddress();
    }

    @Override
    public Response send(Object message) throws RemotingException {
        Client client = getClient();
        Assert.nonNull(client, "Client is null");
        return client.send(message);
    }

    @Override
    public Response send(Object message, int timeout) throws RemotingException {
        Client client = getClient();
        Assert.nonNull(client, "client is null");
        return client.send(message, timeout);
    }

    @Override
    public void close() {
        try {
            doClose();
        } catch (Throwable e) {
            logger.info(e);
        }
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

    protected abstract Client getClient();
}
