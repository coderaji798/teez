package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.NamedThreadFactory;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.common.utl.ObjectUtil;
import pers.coderaji.teez.config.annotation.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author aji
 * @date 2024/7/27 23:31
 * @description 服务配置
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceConfig<T> extends AbstractConfig {

    private static final Logger logger = Logger.getLogger(AbstractConfig.class);

    private String name;

    private Class<?> type;

    private T reference;

    private String version;

    private String group;

    private List<MethodConfig> methods;

    private ApplicationConfig provider;

    private final String serviceName;
    /**
     * 不同注注册中心存不同url
     */
    private final List<URL> urls = new ArrayList<>();

    private final Map<String, String> parameters = new ConcurrentHashMap<>();
    /**
     * 延时暴露执行器
     */
    private static final ScheduledExecutorService delayExportExecutor = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("ServiceDelayExporter", true));


    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
        needDestroy(this);
        this.serviceName = getUniqueName(group, name, version);
        ApplicationConfig.addService(this);
    }

    @Override
    protected void doDestroy() {

    }

    /**
     * 暴露服务
     */
    public synchronized void export() {
        //延迟暴露
        String delay = parameters.get(Constants.DELAY);
        if (ObjectUtil.nonEmpty(delay) && Constants.POSITIVE_NUMBER.matcher(delay).matches() && Integer.parseInt(delay) > 0) {
            delayExportExecutor.schedule(this::doExport, Integer.parseInt(delay), TimeUnit.MILLISECONDS);
        } else {
            doExport();
        }
    }

    protected synchronized void doExport() {
        //校验配置
        Assert.nonEmpty(name, "name is null");
        Assert.nonNull(provider, "provider is null");
        Assert.nonEmpty(provider.getRegistryConfigs(), "registry configs are null");
        Assert.nonNull(provider.getProtocolConfig(), "protocol config is null");
        try {
            type = Class.forName(name, true, Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        if (ObjectUtil.isEmpty(provider.getProtocolConfig().getProtocol())) {
            provider.getProtocolConfig().setProtocol(Constants.TEEZ);
        }
        Integer port = provider.getProtocolConfig().getPort();
        String host = provider.getProtocolConfig().getHost();
        if (ObjectUtil.isEmpty(host) || (Objects.isNull(port) || port < 0)) {
            for (RegistryConfig registry : provider.getRegistryConfigs()) {
                try {
                    try (Socket socket = new Socket()) {
                        SocketAddress address = new InetSocketAddress(registry.getHost(), registry.getPort());
                        socket.connect(address, 1000);
                        host = socket.getLocalAddress().getHostAddress();
                        socket.close();
                        break;
                    }
                } catch (Exception e) {
                    logger.info(e.getMessage(), e);
                }
            }
            provider.getProtocolConfig().setHost(host);
            port = Constants.DEFAULT_PORT;
            while (port > 0 && port < Constants.MAX_PORT) {
                try (Socket socket = new Socket(host, port)) {
                    port++;
                } catch (IOException e) {
                    break;
                }
            }
        }
        //封装URL参数
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.SIDE, Constants.PROVIDER);
        parameters.put(Constants.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        //类名，版本号等信息
        appendParameters(parameters, this, null);
        //方法相关信息
        if (ObjectUtil.nonEmpty(methods)) {
            methods.forEach(method -> {
                appendParameters(parameters, method, method.getName());
                if (ObjectUtil.nonEmpty(method.getArguments())){
                    method.getArguments().forEach(argument -> {
                        String prefix = method.getName() + Constants.DOT + Constants.ARGUMENT;
                        appendParameters(parameters, argument, prefix);
                    });
                }
            });
        }
        //当前服务提供者信息
        appendParameters(parameters, provider, Constants.PROVIDER);
        provider.getRegistryConfigs().forEach(registry -> {

        });

    }
}
