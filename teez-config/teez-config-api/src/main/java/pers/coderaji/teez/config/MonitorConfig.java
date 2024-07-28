package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author aji
 * @date 2024/7/27 23:27
 * @description 监控中心配置
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class MonitorConfig extends AbstractConfig{

    private String protocol;

    private String address;

    private String username;

    private String password;

    private Map<String, String> parameters;
}
