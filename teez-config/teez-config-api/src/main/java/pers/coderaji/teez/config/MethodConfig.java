package pers.coderaji.teez.config;

import lombok.*;

import java.util.List;

/**
 * @author aji
 * @date 2024/7/27 23:45
 * @description 方法配置
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MethodConfig extends AbstractConfig {

    private String name;
    /**
     * 返回参数
     */
    private String callback;

    private List<ArgumentConfig> arguments;
}
