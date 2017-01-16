package com.zjluoyue.concurrency;

/**
 * Created by Jia on 2017/1/14.
 * 线程开启任务
 */
public class BasicThreads {
    public static void main(String[] args) {
//        Thread t = new Thread(new LiftOff());
//        t.start();
//        System.out.println("Waiting for LiftOff");

        //run与debug 模式产生的结果不一样
        for (int i = 0; i < 5; i++) {
            new Thread(new LiftOff()).start();
        }
        System.out.println("Waiting for LiftOff");
    }
}
