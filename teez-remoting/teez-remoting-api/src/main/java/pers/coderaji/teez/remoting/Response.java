package pers.coderaji.teez.remoting;

/**
 * @author aji
 * @date 2024/8/9 23:45
 * @description 响应
 */
public interface Response {

    Object get() throws RemotingException;

    Object get(int timeoutInMillis) throws RemotingException;

    boolean isDone();

}
