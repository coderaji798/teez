package pers.coderaji.teez.remoting;

import lombok.Getter;

import java.net.InetSocketAddress;

/**
 * @author aji
 * @date 2024/8/4 10:30
 * @description 远程处理异常
 */
@Getter
public class RemotingException extends RuntimeException {
    private InetSocketAddress remoteAddress;
    private InetSocketAddress localAddress;

    public RemotingException(InetSocketAddress remoteAddress, InetSocketAddress localAddress, String message, Throwable throwable) {
        super(message, throwable);
        this.remoteAddress = remoteAddress;
        this.localAddress = localAddress;
    }
}
