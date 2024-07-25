package pers.coderaji.teez.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author aji
 * @description @Reference
 * @date 2024/7/25 18:39
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Reference {
    Class<?> type() default void.class;

    String name() default "";

    String version() default "";

    String group() default "";

    String url() default "";
}