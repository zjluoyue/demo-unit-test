package com.zjluoyue.concurrency.comparison;

import com.zjluoyue.arrays.Generated;
import com.zjluoyue.arrays.RandomGenerator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jia on 2017/2/9.
 * 容器测试框架
 */
public abstract class Tester<C> {
    // 启动次数
    static int testReps = 10;
    // 循环次数
    static int testCycles = 1000;
    // 容器大小
    static int containerSize = 1000;
    // 返回初始化后的容器
    abstract C containerInitializer();
    // 启动读取和写入任务
    abstract void startReadersAndWriters();

    C testContainer;
    String testId;
    int nReaders;
    int nWriters;
    volatile long readResult = 0;
    volatile long readTime = 0;
    volatile long writeTime = 0;
    CountDownLatch endLatch;
    static ExecutorService exec =
            Executors.newCachedThreadPool();
    Integer[] writeData;

    Tester(String testId, int nReaders, int nWriters) {
        this.testId = testId + " " + nReaders + "r " + nWriters + "w";
        this.nReaders = nReaders;
        this.nWriters = nWriters;
        writeData = Generated.array(Integer.class,
                new RandomGenerator.Integer(), containerSize);
        for (int i = 0; i < testReps; i++) {
            runTest();
            readTime = 0;
            writeTime = 0;
        }
    }

    void runTest() {
        endLatch = new CountDownLatch(nReaders + nWriters);
        testContainer = containerInitializer();
        startReadersAndWriters();
        try {
            endLatch.await();
        } catch (InterruptedException e) {
            System.out.println("endLatch interrupted");
        }
        System.out.printf("%-27s %14d %14d\n",
                testId, readTime, writeTime);
        if (readTime != 0 && writeTime != 0)
            System.out.printf("%-27s %14d\n",
                    "readTime + writeTime = ", readTime + writeTime);
    }

    abstract class TestTask implements Runnable {
        abstract void test();

        abstract void putResults();
        long duration;

        public void run() {
            long startTime = System.nanoTime();
            test();
            duration = System.nanoTime() - startTime;
            synchronized (Tester.this) {
                putResults();
            }
            endLatch.countDown();
        }
    }

    public static void initMain(String[] args) {
        if (args.length > 0)
            testReps = new Integer(args[0]);
        if (args.length > 1)
            testCycles = new Integer(args[1]);
        if (args.length > 2)
            containerSize = new Integer(args[2]);
        System.out.printf("%-27s %14s %14s\n",
                "Type", "Reader time", "Write time");
    }

}
