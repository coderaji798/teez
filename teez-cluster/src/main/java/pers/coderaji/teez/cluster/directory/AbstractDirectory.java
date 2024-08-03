package pers.coderaji.teez.cluster.directory;

import pers.coderaji.teez.cluster.Directory;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

import java.util.List;

/**
 * @author aji
 * @date 2024/8/3 15:46
 * @description 抽象目录
 */
public abstract class AbstractDirectory<T> implements Directory<T> {

    private final URL directoryUrl;

    public AbstractDirectory(URL directoryUrl) {
        this.directoryUrl = directoryUrl;
    }

    @Override
    public URL getUrl() {
        return directoryUrl;
    }

}
