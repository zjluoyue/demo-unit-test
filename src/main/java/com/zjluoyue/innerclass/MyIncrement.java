package com.zjluoyue.innerclass;

/**
 * Created by zjluoyue on 2016/9/18.
 */
public class MyIncrement {
    public void increment() {
        System.out.println("Other operation");
    }

    static void f(MyIncrement mi) {
        mi.increment();
    }
}
