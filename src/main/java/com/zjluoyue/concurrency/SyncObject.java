package com.zjluoyue.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jia on 2017/1/17.
 */

class DualSynch {
    private Lock lock = new ReentrantLock();
    private Object syncObject = new Object();
    // 在this上同步
    public synchronized void f() {
        for (int i = 0; i < 5; i++) {
            System.out.println("f()");
            Thread.yield();
        }

    }
    // 在syncObject上同步
    public void g() {
        synchronized (syncObject) {
            for (int i = 0; i < 5; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }

    }
}
public class SyncObject {

    public static void main(String[] args) throws InterruptedException {
        final DualSynch ds = new DualSynch();
        new Thread() {
            public void run() {
                ds.f();
            }
        }.start();
//        Thread.sleep(1000);
        ds.g();
    }
}
