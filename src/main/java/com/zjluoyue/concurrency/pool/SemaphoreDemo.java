package com.zjluoyue.concurrency.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/2/8.
 */
class CheckoutTask<T> implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;
    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }
    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + "checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + "checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {

        }
    }

    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}
public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws Exception {
        final Pool<Fat> pool =
                new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckoutTask<Fat>(pool));
        }
        System.out.println("All CheckoutTasks created");
        List<Fat> list = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            Fat f = pool.checkOut();
            System.out.print(i + ": main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> blocked = exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // pool中的对象都被迁出，之后的迁出行为将会被阻塞
                    pool.checkOut();
                } catch (InterruptedException e) {
                    System.out.println("blocked checkOut() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        // 两秒后阻塞被中断
        blocked.cancel(true);
        System.out.println("Checking in objects in " + list);
        // 主线程优先执行，将pool清空，其他线程的迁出将会一直被阻塞，如上一样
        for (Fat fat : list) {
            pool.checkIn(fat);
        }
        // 冗余的迁入将被忽视
        for (Fat fat : list) {
            pool.checkIn(fat);
        }
        exec.shutdown();
    }
}
