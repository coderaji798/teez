package pers.coderaji.teez.config;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aji
 * @date 2024/7/27 23:20
 * @description 应用程序配置
 */
public class ApplicationConfig extends AbstractConfig {

    /**
     * 应用名
     */
    private String name;
    /**
     * 协议
     */
    private ProtocolConfig protocolConfig;
    /**
     * 监控中心配置
     */
    private MonitorConfig monitorConfig;
    /**
     * 注册注销配置
     */
    private List<RegistryConfig> registryConfigs;
    /**
     * 其他参数
     */
    private Map<String, String> parameters;
    /**
     * 服务
     */
    private static final Map<String, ServiceConfig<?>> SERVICES = new ConcurrentHashMap<>();
    /**
     * 引用
     */
    private static final Map<String, ReferenceConfig<?>> REFERENCES = new ConcurrentHashMap<>();

    protected static void addService(ServiceConfig<?> serviceConfig) {
        if (Objects.isNull(serviceConfig)) {
            throw new IllegalArgumentException("serviceConfig is null");
        }
        String name = serviceConfig.getServiceName();
        if (Objects.isNull(name) || name.isEmpty()) {
            name = Objects.isNull(serviceConfig.getName()) || serviceConfig.getName().isEmpty() ? serviceConfig.getType().getName() : serviceConfig.getName();
        }
        SERVICES.put(name, serviceConfig);
    }

    protected static void addReference(ReferenceConfig<?> referenceConfig) {
        if (Objects.isNull(referenceConfig)) {
            throw new IllegalArgumentException("referenceConfig is null");
        }
        String name = referenceConfig.getReferenceName();
        if (Objects.isNull(name) || name.isEmpty()) {
            name = Objects.isNull(referenceConfig.getName()) || referenceConfig.getName().isEmpty() ? referenceConfig.getType().getName() : referenceConfig.getName();
        }
        REFERENCES.put(name, referenceConfig);
    }

    @Override
    protected void doDestroy() {

    }
}
