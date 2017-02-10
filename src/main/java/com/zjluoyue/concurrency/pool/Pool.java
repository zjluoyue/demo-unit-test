package com.zjluoyue.concurrency.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Jia on 2017/2/7.
 * Semaphore 通常用于限制可以访问某些资源（物理或逻辑的）的线程数目
 */
public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<T>();
    private volatile boolean[] checkedOut;
    private Semaphore available;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size,true);

        for (int i = 0; i < size; i++) {
            try {
                items.add(classObject.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public T checkOut() throws InterruptedException {
        // 从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程已被中断。
        available.acquire();
        return getItem();
    }

    public void checkIn(T x) {
        if (releaseItem(x))
            // 释放一个许可，将其返回给信号量
            available.release();
    }

    private synchronized T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) return false; // 不存在
        if (checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false; //
    }
}
