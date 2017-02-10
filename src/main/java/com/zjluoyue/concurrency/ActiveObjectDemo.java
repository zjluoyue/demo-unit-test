package com.zjluoyue.concurrency;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Jia on 2017/2/10.
 * Future 表示异步计算的结果.它提供了检查计算是否完成的方法，以等待计算的完成，并获取计算的结果。
 * 计算完成后只能使用 get 方法来获取结果，如有必要，计算完成前可以阻塞此方法。取消则由 cancel方法来执行。
 * 还提供了其他方法，以确定任务是正常完成还是被取消了。一旦计算完成，就不能再取消计算。
 * 如果为了可取消性而使用 Future 但又不提供可用的结果，则可以声明 Future<?> 形式类型、并返回 null 作为底层任务的结果。
 */
public class ActiveObjectDemo {
    private ExecutorService exec =
            Executors.newCachedThreadPool();
    private Random rand = new Random(47);

    private void pause(int factor) {
        try {
            TimeUnit.MILLISECONDS.sleep(
                    100 + rand.nextInt(factor));
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public Future<Integer> calculateInt(final int x, final int y) {
        return exec.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("starting " + x + " + " + y);
                pause(500);
                return x + y;
            }
        });
    }

    public Future<Float> calculateFloat(final float x, final float y) {
        return exec.submit(new Callable<Float>() {
            @Override
            public Float call() throws Exception {
                System.out.println("starting " + x + " + " + y);
                pause(2000);
                return x + y;
            }
        });
    }

    public void shutdown() {
        exec.shutdown();
    }

    public static void main(String[] args) {
        ActiveObjectDemo d1 = new ActiveObjectDemo();
        List<Future<?>> results =
                new CopyOnWriteArrayList<>();
        for (float f = 0.0f; f < 1.0f; f += 0.2f) {
            results.add(d1.calculateFloat(f, f));
        }
        for (int i = 0; i < 5; i++) {
            results.add(d1.calculateInt(i, i));
        }
        System.out.println("All asynch calls made");
        while (results.size() > 0) {
            for (Future<?> f : results) {
                // 检查是否完成
                if (f.isDone()) {
                    try {
                        System.out.println(f.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    results.remove(f);
                }
            }
        }
        d1.shutdown();
    }
}
