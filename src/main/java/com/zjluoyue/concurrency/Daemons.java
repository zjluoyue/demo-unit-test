package com.zjluoyue.concurrency;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/16.
 */
class Daemon implements Runnable {
    private Thread[] t = new Thread[10];

    @Override
    public void run() {
        for (int i = 0; i < t.length; i++) {
            t[i] = new Thread(new DaemonSpawn());
            // 后台子线程不需要显示设置后台模式，其派生的子线程默认为后台模式
            t[i].start();
            System.out.println("DaemonSpawn " + i + " started, ");
        }
        for (int i = 0; i < t.length; i++) {
            System.out.println("t[" + i + "].isDaemon() = "
                    + t[i].isDaemon() + ", ");
        }
        while (true) {
            Thread.yield();
        }
    }
}

class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while (true) {
            Thread.yield();
        }
    }
}

public class Daemons {
    public static void main(String[] args) throws InterruptedException {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true);
        d.start();
        System.out.println("d.isDaemon() = " + d.isDaemon() + ", ");

        //
        TimeUnit.SECONDS.sleep(1);
    }
}
