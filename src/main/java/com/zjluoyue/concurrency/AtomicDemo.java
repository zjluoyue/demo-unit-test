package com.zjluoyue.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 17/2/25.
 */
class AtomicConster implements Runnable{
    private int local = 0;
    @Override
    public void run() {
        synchronized (this) {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {

            }
            System.out.println(getLocal());;
        }
    }

    public int getLocal() {
        return local++;
    }
}
public class AtomicDemo {
    public static void main(String[] args) {
        AtomicConster ac = new AtomicConster();
        for (int i = 0; i < 10; i++) {
            new Thread(ac).start();
        }
    }
}
