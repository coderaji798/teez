package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.remoting.Response;
import pers.coderaji.teez.rpc.RpcException;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author aji
 * @date 2024/8/4 19:24
 * @description Future适配器
 */
public class FutureAdapter<V> implements Future<V> {

    private final Response response;

    public FutureAdapter(Response response) {
        this.response = response;
    }

    public Response getFuture() {
        return response;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return response.isDone();
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get() {
        try {
            return (V) response.get();
        } catch (Throwable e) {
            throw new RpcException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(long timeout, TimeUnit unit) {
        int timeoutInMillis = (int) TimeUnit.MILLISECONDS.convert(timeout, unit);
        try {
            return (V) response.get(timeoutInMillis);
        } catch (Throwable e) {
            throw new RpcException(e);
        }
    }
}
