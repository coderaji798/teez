package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.annotation.Service;

import java.util.ArrayList;
import java.util.List;

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

    private Class<T> type;

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

    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
        needDestroy(this);
        this.serviceName = getUniqueName(group, name, version);
        ApplicationConfig.addService(this);
    }

    @Override
    protected void doDestroy() {

    }
}
