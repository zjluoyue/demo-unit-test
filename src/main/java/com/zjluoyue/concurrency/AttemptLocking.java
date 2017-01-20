package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jia on 2017/1/17.
 */
public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();

    public void untimed() {
        /** tryLock()
         *  仅在调用时锁未被另一个线程保持的情况下，才获取该锁
         *  如果当前线程已经保持此锁，则将保持计数加 1，该方法将返回 true。
         *  如果锁被另一个线程保持，则此方法将立即返回 false 值。
         */
        boolean captured = lock.tryLock();
        try{
            System.out.println("tryLock(): " + captured);
        } finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean captured = false;
        try{
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        try{
            System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);

        }finally {
            if (captured) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking al = new AttemptLocking();
        al.untimed();
        al.timed();

        new Thread() {
            {setDaemon(true);}

            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();

        // 原书代码，由于性能原因使用下面的代码才能到达效果
//        Thread.yield();
        // 主线程休眠，后台运行线程试图开启锁
        TimeUnit.MILLISECONDS.sleep(10);
        al.untimed();
        al.timed();
    }
}
