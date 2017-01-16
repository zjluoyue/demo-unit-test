package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/14.
 * 实现任务接口，无返回值
 */
public class LiftOff implements Runnable {

    protected int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public LiftOff() {

    }

    public LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + "), ";
    }

    public void run() {
        try {
            while (countDown-- > 0) {
                System.out.print(status());
//              Thread.yield();
                //calling sleep() to pause for a while.
                //old-style:
//                Thread.sleep(100);
                // Java SE5/6-style
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        LiftOff launch = new LiftOff();
//        launch.run();
//    }
}
