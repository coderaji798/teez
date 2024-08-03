package pers.coderaji.teez.registry.support;

import lombok.Setter;
import pers.coderaji.teez.cluster.directory.AbstractDirectory;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.registry.NotifyListener;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.rpc.Invocation;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Protocol;
import pers.coderaji.teez.rpc.RpcException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author aji
 * @date 2024/7/28 11:05
 * @description 注册表目录
 */
@Setter
public class RegistryDirectory<T> extends AbstractDirectory<T> implements NotifyListener {
    private final URL url;
    private final Class<T> type;
    private final String[] methods;
    private final Map<String, String> paramters;

    private Protocol protocol;
    private Registry registry;

    public RegistryDirectory(URL url, Class<T> type) {
        super(url);
        Assert.nonNull(url, "url is null");
        Assert.nonNull(type, "type is null");
        this.url = url;
        this.type = type;
        Map<String, String> map = url.getParameters(Constants.METHODS);
        this.methods = ObjectUtil.isEmpty(map) ? null : new ArrayList<>(map.keySet()).toArray(new String[0]);
        this.paramters = new HashMap<>(url.getParameters());
    }

    @Override
    public void notify(List<URL> urls) {

    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public List<Invoker<T>> list(Invocation invocation) throws RpcException {
        return null;
    }

    public void subscribe(URL url) {
        registry.subscribe(url, this);
    }
}
