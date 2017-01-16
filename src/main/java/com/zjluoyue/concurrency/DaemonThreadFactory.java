package com.zjluoyue.concurrency;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Jia on 2017/1/15.
 */
public class DaemonThreadFactory implements ThreadFactory {
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
