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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        Assert.nonEmpty(name, "");
    }
}
