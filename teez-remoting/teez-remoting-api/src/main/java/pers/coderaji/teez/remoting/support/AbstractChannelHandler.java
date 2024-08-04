package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.remoting.Channel;
import pers.coderaji.teez.remoting.ChannelHandler;
import pers.coderaji.teez.remoting.RemotingException;

/**
 * @author aji
 * @date 2024/8/4 11:21
 * @description 抽象通道处理程序
 */
public abstract class AbstractChannelHandler implements ChannelHandler {

    private final ChannelHandler channelHandler;

    protected AbstractChannelHandler(ChannelHandler channelHandler) {
        Assert.nonNull(channelHandler, "channelHandler is null");
        this.channelHandler = channelHandler;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        channelHandler.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        channelHandler.disconnected(channel);
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        channelHandler.sent(channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        channelHandler.received(channel, message);
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        channelHandler.caught(channel, exception);
    }

    @Override
    public ChannelHandler getHandler() {
        return channelHandler;
    }
}
