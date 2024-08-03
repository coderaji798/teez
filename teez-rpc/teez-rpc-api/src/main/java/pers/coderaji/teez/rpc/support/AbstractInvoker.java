package pers.coderaji.teez.rpc.support;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.*;

import java.util.Map;

/**
 * @author aji
 * @date 2024/8/3 22:03
 * @description 抽象调用器
 */
public abstract class AbstractInvoker<T> implements Invoker<T> {

    private static final Logger logger = Logger.getLogger(AbstractInvoker.class);

    private final Class<T> type;

    private final URL url;

    private final Map<String, String> attachment;

    public AbstractInvoker(Class<T> type, URL url) {
        this(type, url, null);
    }

    public AbstractInvoker(Class<T> type, URL url, Map<String, String> attachment) {
        this.type = type;
        this.url = url;
        this.attachment = attachment;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        Assert.nonFalse(invocation instanceof RpcInvocation, "invocation is wrong");
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        rpcInvocation.setInvoker(this);
        try {
            return doInvoke(rpcInvocation);
        } catch (Throwable throwable) {
            logger.info(throwable);
            return new RpcResult(throwable);
        }
    }


    protected abstract Result doInvoke(Invocation invocation) throws Throwable;
}
