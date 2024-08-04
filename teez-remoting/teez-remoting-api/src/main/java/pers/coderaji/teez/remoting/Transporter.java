package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;

import javax.sound.midi.Receiver;

/**
 * @author aji
 * @date 2024/8/4 15:17
 * @description 数据交换器
 */
@SPI("netty")
public interface Transporter {
    Server bind(URL url, ChannelHandler handler) throws RemotingException;

    Client connect(URL url, ChannelHandler handler) throws RemotingException;
}
