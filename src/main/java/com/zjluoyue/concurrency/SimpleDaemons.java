package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/15.
 * 后台线程，非后台线程存在，后台线程存在；非后台线程结束，后台线程结束
 */
public class SimpleDaemons implements Runnable {
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " " + this);
            }
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All daemons started");
        //主线程休眠，后台线程运行，直到主线程结束，后台线程关闭
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
