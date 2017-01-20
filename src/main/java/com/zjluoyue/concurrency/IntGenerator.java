package com.zjluoyue.concurrency;

/**
 * Created by Jia on 2017/1/17.
 *
 * 数据迭代器
 */
public abstract class IntGenerator {

    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
