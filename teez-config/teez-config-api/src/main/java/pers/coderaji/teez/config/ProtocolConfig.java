package pers.coderaji.teez.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author aji
 * @date 2024/7/27 23:25
 * @description 协议配置
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolConfig extends AbstractConfig {

    private String name;

    private String host;

    private Integer port;

    private Map<String, String> parameters;
}
