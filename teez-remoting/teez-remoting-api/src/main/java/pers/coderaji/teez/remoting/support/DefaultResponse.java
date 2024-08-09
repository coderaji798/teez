package pers.coderaji.teez.remoting.support;

import pers.coderaji.teez.remoting.RemotingException;
import pers.coderaji.teez.remoting.Response;

/**
 * @author aji
 * @date 2024/8/9 23:54
 * @description 默认响应
 */
public class DefaultResponse implements Response {

    private final Object object;

    public DefaultResponse(Object object) {
        this.object = object;
    }

    @Override
    public Object get() throws RemotingException {
        return object;
    }

    @Override
    public Object get(int timeoutInMillis) throws RemotingException {
        return object;
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
