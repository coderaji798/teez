package pers.coderaji.teez.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author aji
 * @date 2024/7/28 9:18
 * @description 参数配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArgumentConfig implements Serializable {

    private String type;

}
