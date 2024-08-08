package pers.coderaji.teez.remoting;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.URL;
import pers.coderaji.teez.common.extension.Adaptive;
import pers.coderaji.teez.common.extension.ExtensionLoader;
import pers.coderaji.teez.common.utl.Assert;

/**
 * @author aji
 * @date 2024/8/8 22:40
 * @description TODO
 */
@Adaptive
public class Transporter$Adaptive implements Transporter {
    @Override
    public Server bind(URL url, ChannelHandler handler) throws RemotingException {
        Assert.nonNull(url, "url is null");
        Assert.nonNull(handler, "handler is null");
        String name = url.getParameter(Constants.TRANSPORTER, Constants.NETTY);
        Transporter transporter = ExtensionLoader.getExtensionLoader(Transporter.class).getExtension(name);
        return transporter.bind(url, handler);
    }

    @Override
    public Client connect(URL url, ChannelHandler handler) throws RemotingException {
        Assert.nonNull(url, "url is null");
        Assert.nonNull(handler, "handler is null");
        String name = url.getParameter(Constants.TRANSPORTER, Constants.NETTY);
        Transporter transporter = ExtensionLoader.getExtensionLoader(Transporter.class).getExtension(name);
        return transporter.connect(url, handler);
    }
}