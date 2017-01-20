package com.zjluoyue.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/20.
 * 生产者-消费者
 */
class Meal {
    private final int orderNum;

    Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    public String toString() {
        return "Meal " + orderNum;
    }
}

/**
 * waitperson等待上餐(chef的信号)
 */
class WaitPerson implements Runnable {
    private Restaurant restaurant;

    WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null) { // 一直等待直到不为null
                        wait();
                    }
                }
                System.out.println("Waitperson got " + restaurant.meal);
                synchronized (restaurant.chef) {
                    restaurant.meal = null; // 从新开始下一订单
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;

    Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    // 安全的信号设计方式
                    while (restaurant.meal != null) { // 等待waitperson处理
                        wait();
                    }
                }
                // 下单
                if (++count == 10) {
                    System.out.println("Out of food, closing");
                    // 没有立即关闭，继续运行调用至sleep，并产生异常
                    restaurant.exec.shutdownNow();
                }
                System.out.println("Order up! ");
                // 首先捕获锁，再将其唤醒
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    // 下单之后将waitperson唤醒
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}
public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
