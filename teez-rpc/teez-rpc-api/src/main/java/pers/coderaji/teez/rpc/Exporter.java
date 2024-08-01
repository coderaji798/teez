package pers.coderaji.teez.rpc;

/**
 * @author aji
 * @date 2024/8/1 23:27
 * @description 数据暴露程序
 */
public interface Exporter<T> {
    Invoker<T> getInvoker();

    /**
     * unexport.
     * <p>
     * <code>
     * getInvoker().destroy();
     * </code>
     */
    void unexport();
}
