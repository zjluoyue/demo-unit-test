package com.zjluoyue.concurrency;

/**
 * Created by Jia on 2017/1/16.
 *
 * 使用自管理Runnable
 */
public class SelfManaged implements Runnable {
    private int countDown = 5;
    private Thread t = new Thread(this);

    public SelfManaged() {
        // 可以访问处于不稳定状态的对象
        // 显示创造一个Thread 并不值得推荐
        t.start();
    }

    public String toString() {
        return Thread.currentThread().getName() + "(" + countDown + "), ";
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(this);
            if (--countDown == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new SelfManaged();
        }
    }
}
