package pers.coderaji.teez.cluster;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.RpcException;

/**
 * @author aji
 * @date 2024/8/3 15:09
 * @description 集群适配器
 */
@Adaptive
public class Cluster$Adaptive implements Cluster {

    public <T> Invoker<T> join(Directory<T> directory) throws RpcException {
        Assert.nonNull(directory, "directory is null");
        Assert.nonNull(directory.getUrl(), "url is null");
        URL directoryUrl = directory.getUrl();
        Cluster cluster = ExtensionLoader.getExtensionLoader(Cluster.class).getExtension(directoryUrl.getProtocol());
        return cluster.join(directory);
    }

}
