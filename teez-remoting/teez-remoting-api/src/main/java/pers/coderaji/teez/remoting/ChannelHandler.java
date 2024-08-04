package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/4 10:30
 * @description 通道处理程序
 */
@SPI
public interface ChannelHandler {

    void connected(Channel channel) throws RemotingException;

    void disconnected(Channel channel) throws RemotingException;

    void sent(Channel channel, Object message) throws RemotingException;

    void received(Channel channel, Object message) throws RemotingException;

    void caught(Channel channel, Throwable exception) throws RemotingException;

    ChannelHandler getHandler();
}
