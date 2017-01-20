package com.zjluoyue.concurrency;

/**
 * Created by Jia on 2017/1/17.
 */
public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    @Override
    public int next() {
        ++currentEvenValue;
        Thread.yield(); // 提高产生奇数的概率
        ++currentEvenValue;
        return currentEvenValue;
    }
    // 线程的交替计算是的数据并不一致，我们需要锁与同步
    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
