package pers.coderaji.teez.rpc;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/3 1:28
 * @description Rpc结果
 */
public class RpcResult implements Result, Serializable {
    private Object result;

    private Throwable exception;

    public RpcResult(Object result) {
        this.result = result;
    }

    public RpcResult(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public boolean hasException() {
        return Objects.nonNull(exception);
    }
}
