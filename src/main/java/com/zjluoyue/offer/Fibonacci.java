package com.zjluoyue.offer;

/**
 * Created by zjluoyue on 2017/1/12.
 */
public class Fibonacci {
    public static int getFibonacci(int n) {
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            b += a;
            a = b - a;
        }
        return a;
    }
}
