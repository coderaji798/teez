package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.remoting.Channel;
import pers.coderaji.teez.remoting.ChannelHandler;

/**
 * @author aji
 * @date 2024/8/4 11:26
 * @description 抽象频道
 */
public abstract class AbstractChannel implements Channel {
    private final ChannelHandler handler;

    private volatile URL url;

    public AbstractChannel(ChannelHandler handler, URL url) {
        Assert.nonNull(handler, "handler is null");
        Assert.nonNull(url, "url is null");
        this.handler = handler;
        this.url = url;
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
    public String toString() {
        return getLocalAddress() + "=====>" + getRemoteAddress();
    }
}
