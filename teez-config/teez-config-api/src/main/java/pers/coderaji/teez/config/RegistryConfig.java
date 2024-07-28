package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author aji
 * @date 2024/7/27 23:29
 * @description 注册注销配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegistryConfig extends AbstractConfig {

    private String protocol;

    private String username;

    private String password;

    private String host;

    private Integer port;

    private Map<String, String> parameters;
}
