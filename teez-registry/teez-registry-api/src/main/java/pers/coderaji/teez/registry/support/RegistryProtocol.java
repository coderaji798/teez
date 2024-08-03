package pers.coderaji.teez.registry.support;

import lombok.Setter;
import pers.coderaji.teez.cluster.Cluster;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.registry.Registry;
import pers.coderaji.teez.registry.RegistryFactory;
import pers.coderaji.teez.rpc.*;
import pers.coderaji.teez.rpc.support.AbstractExporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aji
 * @date 2024/8/2 21:50
 * @description 注册表协议
 */
@Setter
public class RegistryProtocol implements Protocol {

    private final static Logger logger = Logger.getLogger(RegistryProtocol.class);

    private RegistryFactory registryFactory;

    private Protocol protocol;

    private Cluster cluster;

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        Assert.nonNull(invoker, "invoker is null");
        Assert.nonNull(invoker.getUrl(), "invoker url is null");
        URL url = invoker.getUrl();
        //获取注册中心配置信息
        Map<String, String> parameters = url.getParametersAndRemove(Constants.REGISTRY);
        Assert.nonEmpty(parameters, "registry properties is empty");
        URL registryUrl = URL.valueOf(parameters);
        Registry registry = registryFactory.getRegistry(registryUrl);
        //注册service
        Map<String, String> registryServiceMap = url.getParametersAndRemove(Constants.SERVICE);
        registryServiceMap.putAll(url.getParameters());
        URL registryServiceUrl = URL.valueOf(registryServiceMap);
        registry.register(registryServiceUrl);
        return new AbstractExporter<T>(invoker) {
            @Override
            public Invoker<T> getInvoker() {
                return super.getInvoker();
            }
        };
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException {
        Assert.nonNull(type, "type is null");
        Assert.nonNull(url, "url is null");
        //获取注册中心配置信息
        Map<String, String> parameters = url.getParametersAndRemove(Constants.REGISTRY);
        Assert.nonEmpty(parameters, "registry properties is empty");
        URL registryUrl = URL.valueOf(parameters);
        Registry registry = registryFactory.getRegistry(registryUrl);
        //订阅service
        parameters = new HashMap<>(url.getParameters());
        parameters.putAll(url.getParameters(Constants.REFERENCE));
        parameters.put(Constants.SERVICE_NAME, parameters.get(Constants.REFERENCE_NAME));
        parameters.put(Constants.CATEGORY, Constants.PROVIDERS);
        URL referenceUrl = URL.valueOf(parameters);

        RegistryDirectory<T> registryDirectory = new RegistryDirectory<>(referenceUrl, type);
        registryDirectory.setProtocol(protocol);
        registryDirectory.setRegistry(registry);
        registryDirectory.subscribe(referenceUrl);
        return cluster.join(registryDirectory);
    }
}
