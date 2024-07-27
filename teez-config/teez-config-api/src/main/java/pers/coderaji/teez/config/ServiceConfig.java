package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.common.logger.Logger;
import pers.coderaji.teez.config.annotation.Service;

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

    public ServiceConfig(Service service) {
        appendAnnotation(Service.class, service);
        needDestroy(this);
    }

    @Override
    protected void doDestroy() {

    }
}
