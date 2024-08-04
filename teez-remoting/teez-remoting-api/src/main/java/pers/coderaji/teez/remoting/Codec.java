package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.extension.SPI;

import java.io.IOException;
import java.nio.Buffer;

/**
 * @author aji
 * @date 2024/8/4 10:54
 * @description 编码解码器
 */
@SPI
public interface Codec {
    void encode(Channel channel, Buffer buffer, Object message) throws IOException;


    Object decode(Channel channel, Buffer buffer) throws IOException;
}
