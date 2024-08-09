package pers.coderaji.teez.remoting.transport.netty;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.remoting.*;

/**
 * @author aji
 * @date 2024/8/7 23:30
 * @description NettyTransporter
 */
public class NettyTransporter implements Transporter {
    @Override
    public Server bind(URL url, ClientHandler handler) throws RemotingException {
        return new NettyServer(handler, url);
    }

    @Override
    public Client connect(URL url, ClientHandler handler) throws RemotingException {
        return new NettyClient(handler, url);
    }
}
