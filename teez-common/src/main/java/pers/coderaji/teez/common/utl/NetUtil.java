package pers.coderaji.teez.common.utl;

import pers.coderaji.teez.common.logger.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Objects;

/**
 * @author aji
 * @date 2024/8/4 15:36
 * @description 网络相关工具
 */
public class NetUtil {
    private static final Logger logger = Logger.getLogger(NetUtil.class);

    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static volatile InetAddress LOCAL_ADDRESS = null;

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null)
            return LOCAL_ADDRESS;
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
        } catch (Throwable e) {
            logger.info(e);
        }
        //无效IP
        if (isValidAddress(localAddress)) {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                if (interfaces != null) {
                    while (interfaces.hasMoreElements()) {
                        try {
                            NetworkInterface network = interfaces.nextElement();
                            Enumeration<InetAddress> addresses = network.getInetAddresses();
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    logger.info(e);
                                }
                            }
                        } catch (Throwable e) {
                            logger.info(e);
                        }
                    }
                }
            } catch (Throwable e) {
                logger.info(e);
            }
        }
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    private static boolean isValidAddress(InetAddress address) {
        if (Objects.isNull(address) || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (ObjectUtil.nonEmpty(name)
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name));
    }
}
