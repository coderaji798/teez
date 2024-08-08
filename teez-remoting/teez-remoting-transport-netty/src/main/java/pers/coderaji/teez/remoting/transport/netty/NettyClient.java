package pers.coderaji.teez.remoting.transport.netty;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.remoting.Channel;
import pers.coderaji.teez.remoting.ChannelHandler;
import pers.coderaji.teez.remoting.Client;
import pers.coderaji.teez.remoting.support.AbstractClient;

/**
 * @author aji
 * @date 2024/8/7 23:29
 * @description Netty客户端
 */
public class NettyClient extends AbstractClient {

    public NettyClient(ChannelHandler handler, URL url) {
        super(handler, url);
    }

    @Override
    protected void doOpen() throws Throwable {

    }

    @Override
    protected void doClose() throws Throwable {

    }

    @Override
    protected Channel getChannel() {
        return null;
    }
}
