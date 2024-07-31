package pers.coderaji.teez.test;

import pers.coderaji.teez.common.Constants;
import pers.coderaji.teez.common.logger.Logger;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author aji
 * @date 2024/7/31 23:31
 * @description TODO
 */
public class Test0731 {
    private static final Logger logger = Logger.getLogger(Test0731.class);
    volatile static String host = null;
    public static void main(String[] args) {
        int port = Constants.DEFAULT_PORT;
        int finalPort = port;
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(finalPort);
                host = serverSocket.getInetAddress().getHostName();
                Socket accept = serverSocket.accept();
                int port1 = accept.getPort();
                logger.info("port1:{}",port1);
                Reader reader = new InputStreamReader((accept.getInputStream()));
                int read = reader.read();
                logger.info("read:{}",read);
            }catch (Exception exception){
                logger.info("exception:{}",exception.getMessage());
            }
        }).start();
        logger.info("host:{}",host);
        while (port > 0 && port < Constants.MAX_PORT) {
            try (Socket socket = new Socket(host, port)) {
                logger.info("port:{}",port);
                port++;
            }catch (Exception ignore) {
                break;
            }
        }
        logger.info("port:{}",port);
    }
}
