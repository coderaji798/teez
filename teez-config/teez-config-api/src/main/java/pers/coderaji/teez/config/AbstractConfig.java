package pers.coderaji.teez.config;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.logger.Logger;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author aji
 * @date 2024/7/27 22:44
 * @description 抽象配置
 */
public abstract class AbstractConfig implements Serializable {

    private static final Logger logger = Logger.getLogger(AbstractConfig.class);

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

    protected <T> void appendAnnotation(Class<T> type, T annotation) {
        if (Objects.nonNull(type) && Objects.nonNull(annotation)) {
            Method[] methods = type.getMethods();
            for (Method method : methods) {
                if (!Objects.equals(method.getDeclaringClass(), Object.class)
                        && !Objects.equals(method.getReturnType(), void.class)
                        && Objects.equals(method.getParameterTypes().length, 0)
                        && Modifier.isPublic(method.getModifiers())
                        && !Modifier.isStatic(method.getModifiers())) {
                    try {
                        //1.获取注解方法
                        String property = method.getName();
                        //2.拼接当前类的property字段对应的字段的setter
                        String setter = Constants.SET + property.substring(0, 1).toUpperCase() + property.substring(1);
                        Object value = method.invoke(annotation, new Object[0]);
                        if (Objects.nonNull(value)) {
                            try {
                                //3.字段赋值
                                Class<?> parameterType = method.getReturnType();
                                Method setterMethod = getClass().getMethod(setter, new Class<?>[]{parameterType});
                                setterMethod.invoke(this, new Object[]{value});
                            } catch (Exception ignore) {
                            }
                        }
                    } catch (Throwable e) {
                        logger.info(e.getMessage(), e);
                    }
                }
            }
        }
    }

    protected String getUniqueName(String group, String name, String version) {
        StringBuilder buf = new StringBuilder();
        if (Objects.nonNull(group) && !group.isEmpty()) {
            buf.append(group).append("/");
        }
        buf.append(name);
        if (Objects.nonNull(version) && !version.isEmpty()) {
            buf.append(":").append(version);
        }
        return buf.toString();
    }
}
