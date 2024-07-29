package pers.coderaji.teez.common.utl;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/7/29 23:09
 * @description 对象工具
 */
public class ObjectUtil {
    public static boolean nonEmpty(String string) {
        return !Objects.isNull(string) && !string.trim().isEmpty();
    }

    public static boolean isEmpty(String string) {
        return Objects.isNull(string) || string.trim().isEmpty();
    }

    public static boolean nonEmpty(Collection<?> collection) {
        return !Objects.isNull(collection) && !collection.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public static boolean nonEmpty(Map<?, ?> map) {
        return !Objects.isNull(map) && !map.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return Objects.isNull(map) || map.isEmpty();
    }
}
