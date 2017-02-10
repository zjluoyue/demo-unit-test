package com.zjluoyue.concurrency.dinner;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/2/6.
 * 死锁发生的四个条件
 * 1) 互斥条件。任务使用的资源不能共享
 * 2) 至少一个任务他必须持有一个资源且正在等待获取一个当前被其他任务持有的资源
 * 3) 资源不能被抢占，资源释放是普通事件
 * 4) 必须有循环等待
 * 破坏方法之一，条件4，循环等待
 */
// {Args: 0 5 timeout}
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws InterruptedException, IOException {
        int ponder = 5;
        if (args.length > 0) {
            ponder = Integer.parseInt(args[0]);
        }
        int size = 5;
        if (args.length > 1) {
            size = Integer.parseInt(args[1]);
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            sticks[i] = new Chopstick();
        }
        for (int i = 0; i < size; i++) {
            exec.execute(new Philosopher(sticks[i], sticks[(i+1) % size],
                    i, ponder));
        }
        if (args.length == 3 && args[2].equals("timeout")) {
            TimeUnit.SECONDS.sleep(5);
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
