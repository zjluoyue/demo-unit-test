package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/16.
 * 后台进程结束run()方法时，不会执行finally子句
 */
class ADaemon implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Starting ADaemon");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        } finally {
            System.out.println("This should always run?");
        }
    }
}
public class DaemonsDontRunFinally {
    /**
     * 主线程推出后。子线程结束不会运行finally方法
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new ADaemon());
        t.setDaemon(true);
        t.start();
        System.out.println("Starting DaemonsDontRunFinally");
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
