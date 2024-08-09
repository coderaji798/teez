package pers.coderaji.teez.remoting.transport.netty;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.remoting.Client;
import pers.coderaji.teez.remoting.ClientHandler;
import pers.coderaji.teez.remoting.support.AbstractServer;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @author aji
 * @date 2024/8/7 23:28
 * @description Netty服务端
 */
public class NettyServer extends AbstractServer {

    public NettyServer(ClientHandler handler, URL url) {
        super(handler, url);
    }

    @Override
    public Collection<Client> getChannels() {

        return null;
    }

    @Override
    public Client getChannel(InetSocketAddress remoteAddress) {

        return null;
    }

    @Override
    protected void doOpen() throws Throwable {

    }

    @Override
    protected void doClose() throws Throwable {

    }
}
