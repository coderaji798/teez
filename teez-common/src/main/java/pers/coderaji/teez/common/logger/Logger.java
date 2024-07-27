package pers.coderaji.teez.common.logger;

import pers.coderaji.teez.common.Constants;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.*;

/**
 * @author aji
 * @date 2024/7/28 0:54
 * @description 日志
 */
public class Logger {

    static {
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.LOGGING_PROPERTIES);
            if (Objects.nonNull(in)) {
                LogManager.getLogManager().readConfiguration(in);
            } else {
                System.err.println(Constants.LOGGING_PROPERTIES + " does not exist in classpath for logging config!");
            }
        } catch (Throwable t) {
            System.err.println(Constants.LOGGING_PROPERTIES + " is failed to load in classpath for logging config, cause: " + t.getMessage());
        }
    }

    private final java.util.logging.Logger logger;

    private Logger(String name) {
        this.logger = java.util.logging.Logger.getLogger(name);
    }

    public static Logger getLogger(Class<?> key) {
        return new Logger(Objects.isNull(key) ? "" : key.getName());
    }

    public void info(String format, Object... msg) {
        if (isInfoEnabled()) {
            logger.log(Level.INFO, format, msg);
        }
    }

    public void info(String msg, Throwable e) {
        if (isInfoEnabled()) {
            logger.log(Level.INFO, msg, e);
        }
    }

    public void info(Throwable e) {
        if (isInfoEnabled()) {
            logger.log(Level.INFO, e.getMessage(), e);
        }
    }

    private boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }

}
