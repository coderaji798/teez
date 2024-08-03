package pers.coderaji.teez.rpc.support;

import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.Exporter;
import pers.coderaji.teez.rpc.Invoker;

/**
 * @author aji
 * @date 2024/8/3 16:48
 * @description 抽象导出器
 */
public abstract class AbstractExporter<T> implements Exporter<T> {

    private final Invoker<T> invoker;

    public AbstractExporter(Invoker<T> invoker) {
        Assert.nonNull(invoker, "invoker is null");
        Assert.nonNull(invoker.getInterface(), "interface is null");
        Assert.nonNull(invoker.getUrl(), "url is null");
        this.invoker = invoker;
    }

    @Override
    public Invoker<T> getInvoker() {
        return invoker;
    }
}
