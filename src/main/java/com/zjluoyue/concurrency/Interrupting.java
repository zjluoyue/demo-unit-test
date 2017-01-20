package com.zjluoyue.concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/18.
 * 阻塞的中断
 * 不可中断阻塞执行中断会抛出异常
 * 可中断阻塞执行中断后正常执行其他等待任务
 */

// 可中断阻塞
class SleepBlocked implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("sleeping begin");
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

/**
 * I/O开启等待输入，并阻塞其他任务
 * 执行中断将抛出异常
 */
class IOBlocked implements Runnable {
    private InputStream in;

    public IOBlocked(InputStream inputStream) {
        in = inputStream;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read():");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from blocked I/O");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}

/**
 * 在构造方法中获取f()锁，永远不返回，即不释放锁
 * 而在run()中将试图去获取这个锁，如此便会形成一个阻塞来等待锁释放
 */
class SynchronizedBlocked implements Runnable {
    public synchronized void f() {
        while (true) {
            Thread.yield();
        }
    }

    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f();
            }
        }.start();
    }
    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}
public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    static void test(Runnable runnable) throws InterruptedException {
        // 为了可取消性而使用 Future 但又不提供可用的结果，则声明 Future<?> 形式类型、并返回 null 作为底层任务的结果
        Future<?> f = exec.submit(runnable);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + runnable.getClass().getName());
        //试图取消对此任务的执行,如果应该中断执行此任务的线程，则为 true；否则允许正在运行的任务运行完成
        f.cancel(true);
        System.out.println("Interrupt send to " + runnable.getClass().getName());
    }

    public static void main(String[] args) throws Exception {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}
