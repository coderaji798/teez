package pers.coderaji.teez.rpc.teez;

import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.rpc.Exporter;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.support.AbstractExporter;

import java.util.Map;

/**
 * @author aji
 * @date 2024/8/3 18:51
 * @description Teez导出器
 */
public class TeezExporter<T> extends AbstractExporter<T> {

    private final String key;

    private final Map<String, Exporter<?>> exporterMap;

    public TeezExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    @Override
    public void unexport() {
        if (ObjectUtil.nonEmpty(exporterMap)) {
            exporterMap.remove(key);
        }
    }
}
