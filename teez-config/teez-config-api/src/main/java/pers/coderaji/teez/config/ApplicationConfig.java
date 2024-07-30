package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.common.utl.Assert;
import pers.coderaji.teez.common.utl.ObjectUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author aji
 * @date 2024/7/27 23:20
 * @description 应用程序配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
        Assert.nonNull(serviceConfig,"serviceConfig is null");
        String name = serviceConfig.getServiceName();
        if (ObjectUtil.isEmpty(name)) {
            name = ObjectUtil.isEmpty(serviceConfig.getName()) ? serviceConfig.getType().getName() : serviceConfig.getName();
        }
        SERVICES.put(name, serviceConfig);
    }

    protected static void addReference(ReferenceConfig<?> referenceConfig) {
        Assert.nonNull(referenceConfig, "referenceConfig is null");
        String name = referenceConfig.getReferenceName();
        if (ObjectUtil.isEmpty(name)) {
            name = ObjectUtil.isEmpty(referenceConfig.getName()) ? referenceConfig.getType().getName() : referenceConfig.getName();
        }
        REFERENCES.put(name, referenceConfig);
    }

    @Override
    protected void doDestroy() {

    }
}
