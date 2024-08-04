package pers.coderaji.teez.remoting.exchange;

import pers.coderaji.teez.remoting.RemotingException;

/**
 * @author aji
 * @date 2024/8/4 17:59
 * @description TODO
 */
public interface ResponseFuture {

    Object get() throws RemotingException;

    Object get(int timeoutInMillis) throws RemotingException;

    boolean isDone();
}
