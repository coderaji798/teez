package pers.coderaji.teez.rpc.teez.filter;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.remoting.Response;
import pers.coderaji.teez.rpc.*;
import pers.coderaji.teez.rpc.teez.FutureAdapter;

import java.util.concurrent.Future;

/**
 * @author aji
 * @date 2024/8/6 22:54
 * @description 异步过滤器
 */
public class FutureFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Boolean isAsync = invoker.getUrl().getParameter(Constants.ASYNC, Boolean.FALSE);
        Result result = invoker.invoke(invocation);
        if (isAsync) {
            Future<?> future = RpcContext.getContext().getFuture();
            if (future instanceof FutureAdapter) {
                Response response = ((FutureAdapter<?>) future).getFuture();
                result = (Result) response.get();
            }
        }
        return result;
    }
}
