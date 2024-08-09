package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.URL;

import java.net.InetSocketAddress;

/**
 * @author aji
 * @date 2024/8/4 10:47
 * @description 客户端
 */
public interface Client {
    URL getUrl();

    Response send(Object message) throws RemotingException;

    Response send(Object message, int timeout) throws RemotingException;

    ClientHandler getClientHandler();

    InetSocketAddress getRemoteAddress();

    InetSocketAddress getLocalAddress();

    void close();
}
