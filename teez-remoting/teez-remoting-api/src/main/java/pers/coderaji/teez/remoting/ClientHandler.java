package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/4 10:30
 * @description 通道处理程序
 */
@SPI
public interface ClientHandler {

    void sent(Client client, Object message) throws RemotingException;

    void received(Client client, Object message) throws RemotingException;

    ClientHandler getHandler();
}
