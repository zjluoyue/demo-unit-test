package com.zjluoyue.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jia on 2017/1/17.
 */
public class AtomicEvenGenerator extends IntGenerator {
    private AtomicInteger currentEvenValue = new AtomicInteger(0);

    @Override
    public int next() {
        return currentEvenValue.addAndGet(2);
    }

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenGenerator());
    }
}
