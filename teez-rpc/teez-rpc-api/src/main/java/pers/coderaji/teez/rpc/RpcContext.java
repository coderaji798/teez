package pers.coderaji.teez.rpc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author aji
 * @date 2024/8/4 18:03
 * @description Rpc上下文
 */
@Data
public class RpcContext {

    private static final ThreadLocal<RpcContext> LOCAL = new ThreadLocal<RpcContext>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    private Future<?> future;

    public static RpcContext getContext() {
        return LOCAL.get();
    }
}
