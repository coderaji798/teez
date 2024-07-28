package pers.coderaji.teez.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @author aji
 * @description 统一资源定位地址
 * @date 2024/7/25 11:49
 */
@Getter
@EqualsAndHashCode
public final class URL implements Serializable {
    private final String protocol;

    private final String username;

    private final String password;

    private final String host;

    private final int port;

    private final String path;

    private final Map<String, String> parameters;

    public URL(String protocol, String username, String password, String host, int port, String path, Map<String, String> parameters) {
        this.protocol = protocol;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public static URL valueOf(String url) {
        if (Objects.isNull(url) || (url = url.trim()).isEmpty()) {
            throw new IllegalArgumentException("url is empty");
        }
        // protocol://username:password@host:port/path?param1=val1&param2=val2
        Map<String, String> parameters = null;
        //1.参数与其余部分分割
        int index = url.indexOf("?");
        if (index > 0) {
            //参数根据&符号分割
            String[] split = url.substring(index + 1).split("\\&");
            parameters = new HashMap<>();
            for (String string : split) {
                //元素不为空
                string = string.trim();
                if (!string.isEmpty()) {
                    //判断是不是键值对
                    int i = string.indexOf("=");
                    if (i >= 0) {
                        parameters.put(string.substring(0, i), string.substring(i + 1));
                    } else {
                        //不是键值对，键等于值
                        parameters.put(string, string);
                    }
                }
            }
            url = url.substring(0, index);
        }
        //2.其余部分解析
        String protocol = null;
        String username = null;
        String password = null;
        String host = null;
        int port = -1;
        String path = null;
        //3.获取协议
        index = url.indexOf("://");
        if (index <= 0) {
            throw new RuntimeException("url missing protocol :" + url);
        }
        protocol = url.substring(0, index);
        url = url.substring(index + 3);
        //4.获取路径
        index = url.indexOf("/");
        if (index >= 0) {
            path = url.substring(index + 1);
            url = url.substring(0, index);
        }
        //5.获取用户名和密码
        index = url.lastIndexOf("@");
        if (index > 0) {
            //用户名密码
            String up = url.substring(0, index);
            int i = up.indexOf(":");
            if (i >= 0) {
                if (i > 0) {
                    username = up.substring(0, i);
                }
                password = up.substring(i + 1);
            }
            url = url.substring(index + 1);
        }
        //6.获取端口
        index = url.indexOf(":");
        if (index >= 0) {
            String s = url.substring(index + 1);
            if (!s.isEmpty()) {
                port = Integer.parseInt(s);
            }
            url = url.substring(0, index);
        }
        //7.域名
        host = url;
        return new URL(protocol, username, password, host, port, path, parameters);
    }

    public static URL valueOf(Map<String, String> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            throw new IllegalArgumentException("map is empty");
        }
        String protocol = map.remove("protocol");
        if (Objects.isNull(protocol) || protocol.trim().isEmpty()) {
            throw new IllegalArgumentException("protocol is empty");
        }
        String username = map.remove("protocol");
        String password = map.remove("password");
        String host = map.remove("host");
        int port = -1;
        String string = map.remove("port");
        if (Objects.nonNull(string) && !string.trim().isEmpty()) {
            port = Integer.parseInt(string);
        }
        String path = map.remove("path");
        Map<String, String> parameters = new HashMap<>(map);
        return new URL(protocol, username, password, host, port, path, parameters);
    }

    public String urlString() {
        StringBuilder builder = new StringBuilder();
        if (Objects.nonNull(protocol) && !protocol.isEmpty()) {
            builder.append(protocol).append("://");
        }
        if (Objects.nonNull(username) && !username.isEmpty()) {
            builder.append(username).append(":");
            if (Objects.nonNull(password) && !password.isEmpty()) {
                builder.append(username);
            }
            builder.append("@");
        }
        if (Objects.nonNull(host) && !host.isEmpty()) {
            builder.append(host);
            if (port > 0) {
                builder.append(":").append(host);
            }
        }
        if (Objects.nonNull(path) && !path.isEmpty()) {
            builder.append("/").append(path);
        }
        if (Objects.nonNull(parameters) && !parameters.isEmpty()) {
            builder.append("?");
            Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> next = iterator.next();
                builder.append(next.getKey()).append("=").append(next.getValue());
                if (iterator.hasNext()) {
                    builder.append("&");
                }
            }
        }
        return builder.toString();
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }
    public <T> T getParameter(String key, T defaultValue) {
        String value = parameters.get(key);
        if (Objects.isNull(value) || Objects.equals(Constants.NULL, value) || Objects.isNull(defaultValue)) {
            return defaultValue;
        }
        return convert(defaultValue, value);
    }

    private <T> T convert(T defaultValue, String value) {
        if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(value);
        } else if (defaultValue instanceof Byte) {
            return (T) Byte.valueOf(value);
        } else if (defaultValue instanceof Short) {
            return (T) Short.valueOf(value);
        } else if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(value);
        } else if (defaultValue instanceof Long) {
            return (T) Long.valueOf(value);
        } else if (defaultValue instanceof Float) {
            return (T) Float.valueOf(value);
        } else if (defaultValue instanceof Double) {
            return (T) Double.valueOf(value);
        } else if (defaultValue instanceof Character) {
            return (T) Character.valueOf(value.charAt(0));
        } else if (defaultValue instanceof String) {
            return (T) value;
        } else {
            throw new IllegalArgumentException(String.format("%s is not definition", defaultValue.getClass()));
        }
    }

    public String getAddress() {
        return port <= 0 ? host : host + ":" + port;
    }
}
