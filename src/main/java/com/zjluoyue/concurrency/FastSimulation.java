package com.zjluoyue.concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jia on 2017/2/10.
 */
public class FastSimulation {
    static final int N_ELEMENTS = 100000;
    static final int N_GENES = 30;
    static final int N_EVOLVERS = 50;
    static final AtomicInteger[][] GRID = new AtomicInteger[N_ELEMENTS][N_GENES];
    static Random random = new Random(47);

    static class Evolver implements Runnable {

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                int element = random.nextInt(N_ELEMENTS);
                for (int i = 0; i < N_GENES; i++) {
                    int prevoius = element - 1;
                    int next = element + 1;
                    if (prevoius < 0)
                        prevoius = N_ELEMENTS - 1;
                    if (next >= N_ELEMENTS)
                        next = 0;
                    int oldValue = GRID[element][i].get();
                    int newValue = oldValue + GRID[prevoius][i].get() + GRID[next][i].get();
                    newValue /= 3;
                    // 乐观锁，如果值在该线程执行中未改变则set新值,返回true
                    if (!GRID[element][i].compareAndSet(oldValue, newValue)) {
                        System.out.println("Old value changed from " + oldValue);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < N_ELEMENTS; i++) {
            for (int j = 0; j < N_GENES; j++) {
                GRID[i][j] = new AtomicInteger(random.nextInt(1000));
            }
        }
        for (int i = 0; i < N_EVOLVERS; i++) {
            exec.execute(new Evolver());
        }
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
