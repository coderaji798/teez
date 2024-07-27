package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.coderaji.teez.config.annotation.Reference;

import java.util.List;

/**
 * @author aji
 * @date 2024/7/27 23:30
 * @description 引用配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ReferenceConfig<T> extends AbstractConfig {

    private String name;

    private Class<T> type;

    private String version;

    private String group;

    private String url;

    private List<MethodConfig> methods;

    private ApplicationConfig consumer;

    public ReferenceConfig(Reference reference) {
        appendAnnotation(Reference.class, reference);
        needDestroy(this);
    }

    @Override
    protected void doDestroy() {

    }
}
