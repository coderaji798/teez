package pers.coderaji.teez.rpc;

import java.util.Map;

/**
 * @author aji
 * @date 2024/8/1 23:01
 * @description 调用
 */
public interface Invocation {
    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getArguments();

    Invoker<?> getInvoker();
}
