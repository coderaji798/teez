package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.URL;

import java.net.InetSocketAddress;

/**
 * @author aji
 * @date 2024/8/4 10:16
 * @description 频道
 */
public interface Channel {

    URL getUrl();

    void send(Object message) throws RemotingException;

    ChannelHandler getChannelHandler();

    InetSocketAddress getRemoteAddress();

    InetSocketAddress getLocalAddress();

    void close();
}
