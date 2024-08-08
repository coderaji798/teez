package pers.coderaji.teez.remoting.transport.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.remoting.Channel;
import pers.coderaji.teez.remoting.ChannelHandler;
import pers.coderaji.teez.remoting.RemotingException;
import pers.coderaji.teez.remoting.Server;
import pers.coderaji.teez.remoting.support.AbstractServer;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/7 23:28
 * @description Netty服务端
 */
public class NettyServer extends AbstractServer {

    public NettyServer(ChannelHandler handler, URL url) {
        super(handler, url);
    }

    @Override
    public Collection<Channel> getChannels() {

        return null;
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {

        return null;
    }

    @Override
    protected void doOpen() throws Throwable {

    }

    @Override
    protected void doClose() throws Throwable {

    }
}
