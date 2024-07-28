package pers.coderaji.teez.common;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author aji
 * @date 2024/7/28 14:43
 * @description 线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger num = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemon;

    private final ThreadGroup group;

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix + "-thread-";
        this.daemon = daemon;
        SecurityManager manager = System.getSecurityManager();
        this.group = Objects.isNull(manager) ? Thread.currentThread().getThreadGroup() : manager.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + num.getAndIncrement();
        Thread ret = new Thread(group, r, name, 0);
        ret.setDaemon(daemon);
        return ret;
    }
}
