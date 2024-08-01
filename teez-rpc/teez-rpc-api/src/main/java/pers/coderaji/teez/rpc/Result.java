package pers.coderaji.teez.rpc;

import java.util.Map;

/**
 * @author aji
 * @date 2024/8/1 22:59
 * @description 结果
 */
public interface Result {
    Object getValue();


    Throwable getException();


    boolean hasException();

}
