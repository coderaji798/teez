package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.remoting.ExchangeClient;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Result;
import pers.coderaji.teez.rpc.RpcInvocation;
import pers.coderaji.teez.rpc.support.AbstractInvoker;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aji
 * @date 2024/8/3 21:54
 * @description Teez调用器
 */

public class TeezInvoker<T> extends AbstractInvoker<T> {


    private final AtomicInteger index = new AtomicInteger(0);

    private final ExchangeClient[] clients;

    private final Set<Invoker<?>> invokers;


    public TeezInvoker(Class<T> type, URL url, Map<String, String> attachment, ExchangeClient[] clients, Set<Invoker<?>> invokers) {
        super(type, url, attachment);
        this.clients = clients;
        this.invokers = invokers;
    }


    @Override
    protected Result doInvoke(final Invocation invocation) throws Throwable {
        RpcInvocation rpcInvocation = (RpcInvocation) invocation;
        ExchangeClient currentClient;
        if (Objects.equals(clients.length, 1)) {
            currentClient = clients[0];
        } else if (clients.length > 1) {
            currentClient = clients[index.getAndIncrement() % clients.length];
        }
        Integer timeout = getUrl().getParameter(Constants.TIMEOUT, Constants.DEFAULT_TIMEOUT);
        Boolean isAsync = getUrl().getParameter(Constants.ASYNC, Boolean.FALSE);
        if (isAsync) {

        } else {

        }
        return null;
    }
}
