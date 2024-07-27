package pers.coderaji.teez.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author aji
 * @date 2024/7/27 22:44
 * @description 抽象配置
 */
public abstract class AbstractConfig implements Serializable {

    private static final Set<AbstractConfig> configs = new HashSet<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //do something before Teez shutdown
            configs.forEach(AbstractConfig::doDestroy);
        }, "Teez config shutdown hook"));
    }

    protected static void needDestroy(AbstractConfig config) {
        configs.add(config);
    }

    protected abstract void doDestroy();
}
