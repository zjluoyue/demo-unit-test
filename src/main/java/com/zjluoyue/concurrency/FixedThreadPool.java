package com.zjluoyue.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jia on 2017/1/15.
 */
public class FixedThreadPool {
    public static void main(String[] args) {
        //Constructor argument is number of threads
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
