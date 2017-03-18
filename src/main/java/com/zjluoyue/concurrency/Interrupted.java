package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 17/2/25.
 * 在声明中断异常的之前，中断标志将会被清除
 */
class SleepRunner implements Runnable{
    boolean interrupted;
    @Override
    public void run() {
        // 如若产生中断 Thread.interrupted()方法先返回该线程的中断状态，然后清除中断标志
        // 也就是一个线程中断（不出现中断异常的情况）第一次调用时返回true，其后调用均为false
        // 如果产生中断异常，处理方式为，先将该线程的中断标志清除，然后抛出异常
        while (!(interrupted = Thread.interrupted())) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {

            } finally {
                System.out.println("SleepThread interrupted " + interrupted);
//                System.out.println("IN SleepThread interrupted is " + Thread.interrupted());
            }
        }

//        System.out.println("IN SleepThread interrupted is " + Thread.interrupted());
    }
}

class BusyRunner implements Runnable {
    boolean interrupted = false;
    @Override
    public void run() {
        while (!(interrupted = Thread.currentThread().isInterrupted())) {

        }
        System.out.println(interrupted);
//        System.out.println("IN BusyThread interrupted is " + Thread.interrupted());
    }
}
public class Interrupted {
    public static void main(String[] args) throws InterruptedException {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepRunner");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();

        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
//        System.out.println("Thread interrupted is " + Thread.interrupted());
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
//        System.out.println("Thread interrupted is " + Thread.interrupted());
        TimeUnit.SECONDS.sleep(2);
    }
}
