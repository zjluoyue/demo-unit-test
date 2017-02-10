package com.zjluoyue.concurrency.dinner;

/**
 * Created by Jia on 2017/2/6.
 */
public class Chopstick {
    // 筷子的状态，没被拿起false，拿起为true
    private boolean taken = false;

    // 哲学家尝试拿筷子
    public synchronized void take()
            throws InterruptedException {
        // 筷子状态为false，哲学家可以拿
        // 筷子状态为true，哲学家不能拿
        while (taken) {
            wait();
        }
        taken = true;
    }

    // 哲学家放下筷子
    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}
