package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.remoting.*;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @author aji
 * @date 2024/8/4 16:28
 * @description 抽象服务器
 */
public abstract class AbstractServer implements Server {

    private static final Logger logger = Logger.getLogger(AbstractServer.class);

    private final ChannelHandler handler;

    private volatile URL url;

    private Codec codec;

    private InetSocketAddress localAddress;

    public AbstractServer(ChannelHandler handler, URL url) {
        Assert.nonNull(handler, "handler is null");
        Assert.nonNull(url, "url is null");
        this.handler = handler;
        this.url = url;
        String codecName = url.getParameter(Constants.CODEC, Constants.TEEZ);
        this.codec = ExtensionLoader.getExtensionLoader(Codec.class).getExtension(codecName);
        this.localAddress = getUrl().toInetSocketAddress();
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
    public ChannelHandler getChannelHandler() {
        return handler;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    @Override
    public void send(Object message) throws RemotingException {
        Collection<Channel> channels = getChannels();
        for (Channel channel : channels) {
            channel.send(message);
        }
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
}
