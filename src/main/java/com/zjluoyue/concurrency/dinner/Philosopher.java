package com.zjluoyue.concurrency.dinner;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/2/6.
 * 用ponder因子来调整思考的时间，数值越小死锁发生越快
 */
public class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(47);

    private void pause() throws InterruptedException {
        if (ponderFactor == 0) {
            return;
        }
        TimeUnit.MILLISECONDS.sleep(ponderFactor * 250);
    }

    public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
        this.left = left;
        this.right = right;
        this.id = ident;
        this.ponderFactor = ponder;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " " + "thinking");
                pause();
                System.out.println(this + " " + "grabbing right");
                right.take();
                System.out.println(this + " " + "grabbing left");
                left.take();
                System.out.println(this + " " + "eating");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " " + "exiting via interrupt");
        }
    }

    public String toString() {
        return "Philosopher " + id;
    }
}

