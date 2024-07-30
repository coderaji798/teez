package pers.coderaji.teez.common.utl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/7/29 23:18
 * @description 断言工具
 */
public class Assert {
    public static void nonEmpty(String string, String msg) {
        if (ObjectUtil.isEmpty(string)) {
            throw new AssertionError(msg);
        }
    }

    public static void nonEmpty(Collection<?> collection, String msg) {
        if (ObjectUtil.isEmpty(collection)) {
            throw new AssertionError(msg);
        }
    }

    public static void nonEmpty(Map<?, ?> map, String msg) {
        if (ObjectUtil.isEmpty(map)) {
            throw new AssertionError(msg);
        }
    }

    public static void nonNull(Object object, String msg) {
        if (Objects.isNull(object)) {
            throw new AssertionError(msg);
        }
    }
    public static void nonFalse(boolean bool, String msg) {
        if (!bool) {
            throw new AssertionError(msg);
        }
    }
}
