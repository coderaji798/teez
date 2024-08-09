package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.SPI;

/**
 * @author aji
 * @date 2024/8/4 15:17
 * @description 数据交换器
 */
@SPI(Constants.NETTY)
public interface Transporter {
    Server bind(URL url, ClientHandler handler) throws RemotingException;

    Client connect(URL url, ClientHandler handler) throws RemotingException;
}
