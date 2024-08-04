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

    private final ChannelHandler handler;

    private volatile URL url;

    private Codec codec;

    public AbstractClient(ChannelHandler handler, URL url) {
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
    public ChannelHandler getChannelHandler() {
        return handler;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        Channel channel = getChannel();
        if (Objects.isNull(channel)) {
            return getUrl().toInetSocketAddress();
        }
        return channel.getRemoteAddress();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        Channel channel = getChannel();
        if (Objects.isNull(channel)) {
            return InetSocketAddress.createUnresolved(NetUtil.getLocalHost(), 0);
        }
        return channel.getLocalAddress();
    }

    @Override
    public Object getAttribute(String key) {
        Channel channel = getChannel();
        if (Objects.isNull(channel))
            return null;
        return channel.getAttribute(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        Channel channel = getChannel();
        if (Objects.isNull(channel))
            return;
        channel.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        Channel channel = getChannel();
        if (Objects.isNull(channel))
            return;
        channel.removeAttribute(key);
    }

    @Override
    public boolean hasAttribute(String key) {
        Channel channel = getChannel();
        if (Objects.isNull(channel))
            return false;
        return channel.hasAttribute(key);
    }

    @Override
    public void send(Object message) throws RemotingException {
        Channel channel = getChannel();
        Assert.nonNull(channel, "channel is null");
        channel.send(message);
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

    protected abstract Channel getChannel();
}
