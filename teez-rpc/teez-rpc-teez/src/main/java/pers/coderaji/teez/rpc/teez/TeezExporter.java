package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.rpc.Exporter;
import pers.coderaji.teez.rpc.Invoker;

/**
 * @author aji
 * @date 2024/8/3 18:51
 * @description Teez导出器
 */
public class TeezExporter<T> implements Exporter<T> {
    @Override
    public Invoker<T> getInvoker() {
        return null;
    }
}
