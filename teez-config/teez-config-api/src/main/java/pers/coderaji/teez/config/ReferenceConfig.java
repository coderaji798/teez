package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.config.annotation.Reference;
import pers.coderaji.teez.rpc.Invoker;
import pers.coderaji.teez.rpc.Protocol;
import pers.coderaji.teez.rpc.ProxyFactory;

import java.util.*;

/**
 * @author aji
 * @date 2024/7/27 23:30
 * @description 引用配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReferenceConfig<T> extends AbstractConfig {

    private static final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private static final ProxyFactory proxyFactory = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private String name;

    private Class<T> type;

    private String version;

    private String group;

    private String url;

    private List<MethodConfig> methods;

    private ApplicationConfig consumer;

    private final String referenceName;

    /**
     * 提供者列表
     */
    private final List<URL> urls = new ArrayList<>();

    private transient T proxyReference;

    private transient volatile Invoker<?> invoker;


    public ReferenceConfig(Reference reference) {
        appendAnnotation(Reference.class, reference);
        needDestroy(this);
        this.referenceName = getUniqueName(group, name, version);
        ApplicationConfig.addReference(this);
    }

    @Override
    protected void doDestroy() {

    }

    @SuppressWarnings("unchecked")
    public synchronized T get() {
        if (Objects.isNull(proxyReference)) {
            Assert.nonEmpty(name, "name is empty");
            Assert.nonNull(consumer, "provider is null");
            Assert.nonEmpty(consumer.getRegistryConfigs(), "registry configs are null");
            Assert.nonNull(consumer.getProtocolConfig(), "protocol config is null");
            try {
                type = (Class<T>) Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
            checkMethods(type, methods);
            //设置默认协议
            if (ObjectUtil.isEmpty(consumer.getProtocolConfig().getProtocol())) {
                consumer.getProtocolConfig().setProtocol(Constants.TEEZ);
            }
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.SIDE, Constants.CONSUMER);
            parameters.put(Constants.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
            //类名，版本号等信息
            appendParameters(parameters, this, Constants.REFERENCE);
            appendParameters(parameters, consumer.getProtocolConfig(), Constants.REFERENCE);
            if (ObjectUtil.nonEmpty(methods)) {
                methods.forEach(method -> {
                    String prefix1 = Constants.METHODS + Constants.DOT + method.getName();
                    appendParameters(parameters, method, prefix1);
                    if (ObjectUtil.nonEmpty(method.getArguments())) {
                        method.getArguments().forEach(argument -> {
                            String prefix = prefix1 + Constants.DOT + Constants.ARGUMENT;
                            appendParameters(parameters, argument, prefix);
                        });
                    }
                });
            }
            //设置了特定访问路径
            if (ObjectUtil.nonEmpty(url)) {
                //如果没有指定协议
                if (!url.contains("://")) {
                    url = consumer.getProtocolConfig().getProtocol() + "://" + url;
                }
                URL remoteUrl = URL.valueOf(url);
                if (ObjectUtil.isEmpty(remoteUrl.getPath())) {
                    remoteUrl = remoteUrl.setPath(name);
                }
                urls.add(remoteUrl);
            } else {
                for (RegistryConfig registryConfig : consumer.getRegistryConfigs()) {
                    appendParameters(parameters, registryConfig, Constants.REGISTRY);
                    parameters.put(Constants.PROTOCOL, Constants.REGISTRY);
                    URL registryUrl = URL.valueOf(parameters);
                    urls.add(registryUrl);
                }
            }
            //引用远程服务（订阅）
            Assert.nonEmpty(urls, "urls is empty");
            if (Objects.equals(urls.size(), 1)) {
                invoker = protocol.refer(type, urls.get(0));
            } else {
                List<Invoker<?>> invokers = new ArrayList<>();
                for (URL url : urls) {
                    invokers.add(protocol.refer(type, url));
                }
                //TODO 集群操作
            }
            Assert.nonNull(invoker, String.format("no provider available for %s", referenceName));
            proxyReference = (T) proxyFactory.getProxy(invoker);
        }
        return proxyReference;
    }
}
