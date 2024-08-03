package pers.coderaji.teez.rpc;

import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aji
 * @date 2024/8/3 1:09
 * @description TODO
 */
@Setter
public class RpcInvocation implements Invocation, Serializable {

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private transient Invoker<?> invoker;

    public RpcInvocation() {
    }

    public RpcInvocation(Method method, Object[] arguments) {
        this(method.getName(), method.getParameterTypes(), arguments, null);
    }

    public RpcInvocation(String methodName, Class<?>[] parameterTypes, Object[] arguments, Invoker<?> invoker) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
        this.arguments = arguments == null ? new Object[0] : arguments;
        this.invoker = invoker;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Invoker<?> getInvoker() {
        return invoker;
    }
}
