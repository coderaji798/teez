package pers.coderaji.teez.cluster.support;

import pers.coderaji.teez.cluster.Directory;
import pers.coderaji.teez.cluster.LoadBalance;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Result;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/3 16:12
 * @description 故障转移群集调用器
 */
public class FailoverClusterInvoker<T> extends AbstractClusterInvoker<T> {

    private static final Logger logger = Logger.getLogger(FailoverClusterInvoker.class);

    public FailoverClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(Invocation invocation, final List<Invoker<T>> invokers, LoadBalance loadbalance) throws RpcException {
        checkInvokers(invokers);
        if (Objects.isNull(loadbalance)) {
            loadbalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(Constants.RANDOM);
        }
        URL url = getUrl();
        Integer retry = url.getParameter(Constants.RETRY, Constants.RETRY_TIME);
        retry = retry <= 0 ? Constants.RETRY_TIME : retry;
        for (int i = 0; i < retry; i++) {
            Invoker<T> invoker = loadbalance.select(invokers, url, invocation);
            try {
                Result result = invoker.invoke(invocation);
                logger.info("method call successful, although retry {} time(s)", i + 1);
                return result;
            } catch (Exception e) {

            }
        }
        throw new RpcException(String.format("method call failed, although retry %s time(s)", retry));
    }
}
