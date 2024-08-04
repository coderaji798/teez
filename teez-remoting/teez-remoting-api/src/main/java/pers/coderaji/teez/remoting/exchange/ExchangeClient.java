package pers.coderaji.teez.remoting.exchange;


import pers.coderaji.teez.remoting.Client;
import pers.coderaji.teez.remoting.RemotingException;

/**
 * @author aji
 * @date 2024/8/3 21:57
 * @description 数据交换客户端
 */
public interface ExchangeClient extends Client {
    ResponseFuture request(Object request) throws RemotingException;

    ResponseFuture request(Object request, int timeout) throws RemotingException;
}
