package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.remoting.Client;
import pers.coderaji.teez.remoting.ClientHandler;
import pers.coderaji.teez.remoting.RemotingException;

/**
 * @author aji
 * @date 2024/8/4 11:21
 * @description 抽象通道处理程序
 */
public abstract class AbstractClientHandler implements ClientHandler {
    public abstract Object reply(Client channel, Object msg) throws RemotingException;
}
