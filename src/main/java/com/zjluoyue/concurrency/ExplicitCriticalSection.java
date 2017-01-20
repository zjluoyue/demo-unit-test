package com.zjluoyue.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jia on 2017/1/17.
 */
// Synchronize the entire method
class ExplicitPairManager1 extends PairManager {
    private Lock lock = new ReentrantLock();
    @Override
    public synchronized void increment() {
        lock.lock();
        try{
            p.incrementX();
            p.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }
}

/**
 * use a critical section:
 * 会出现异常，pair不是一个线程安全的类
 */
class ExplicitPairManager2 extends PairManager {
    private Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        Pair temp;
        lock.lock();
        try{
            p.incrementY();
            p.incrementX();
            System.out.println(p);
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}
public class ExplicitCriticalSection {
    public static void main(String[] args) {
        PairManager
                pman1 = new ExplicitPairManager1(),
                pman2 = new ExplicitPairManager2();
//        CriticalSection.testApproaches(pman1, pman2);

        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator pm = new PairManipulator(pman2);
        PairChecker pc = new PairChecker(pman2);
        exec.execute(pm);
        exec.execute(pc);

    }
}
