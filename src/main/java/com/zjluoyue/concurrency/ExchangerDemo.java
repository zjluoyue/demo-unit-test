package com.zjluoyue.concurrency;

import com.zjluoyue.concurrency.pool.Fat;
import com.zjluoyue.generics.BasicGenerator;
import com.zjluoyue.generics.Generator;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Jia on 2017/2/8.
 * Exchanger 可以在对中对元素进行配对和交换的线程的同步点
 */
// full
class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
        exchanger = exchg;
        generator = gen;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i = 0; i < ExchangerDemo.size; i++)
                    holder.add(generator.next());
                // 等待另一个线程到达此交换点（除非当前线程被中断），然后将给定的对象传送给该线程，并接收该线程的对象。
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {

        }
    }
}
// empty
class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
        exchanger = ex;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for (T t : holder) {
                    value = t;
                    holder.remove(t);
                }
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Final value: " + value);
    }
}
public class ExchangerDemo {
    // 产生的List的大小
    static int size = 10;
    static int delay = 5;

    public static void main(String[] args) throws Exception {
        if (args.length > 0)
            size = new Integer(args[0]);
        if (args.length > 1)
            delay = new Integer(args[1]);

        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<>();
        // CopyOnWriteArray 元素遍历时可以被删除(remove)
        List<Fat>
                producerList = new CopyOnWriteArrayList<>(),
                consumerList = new CopyOnWriteArrayList<>();
        exec.execute(new ExchangerProducer<Fat>(xc,
                BasicGenerator.create(Fat.class), producerList));
        exec.execute(new ExchangerConsumer<Fat>(xc, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
}
