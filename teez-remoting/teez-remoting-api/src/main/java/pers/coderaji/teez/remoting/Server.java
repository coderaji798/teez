package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.URL;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @author aji
 * @date 2024/8/4 10:48
 * @description 服务端
 */
public interface Server {

    URL getUrl();

    Collection<Client> getChannels();

    Client getChannel(InetSocketAddress remoteAddress);

    ClientHandler getClientHandler();

    InetSocketAddress getLocalAddress();

    void send(Object message) throws RemotingException;

    void close();
}
