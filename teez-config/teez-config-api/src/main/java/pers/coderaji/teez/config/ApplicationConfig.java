package pers.coderaji.teez.config;

import java.util.List;
import java.util.Map;
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



    @Override
    protected void doDestroy() {

    }
}
