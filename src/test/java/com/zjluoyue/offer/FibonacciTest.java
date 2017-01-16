package com.zjluoyue.offer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zjluoyue on 2017/1/12.
 */
public class FibonacciTest {
    @Test
    public void getFibonacciTest() {
//        for (int i = 0; i <= 39; i++) {
//            System.out.println(i + ": " + Fibonacci.getFibonacci(i));
//        }

        Assert.assertEquals(0, Fibonacci.getFibonacci(0));
        Assert.assertEquals(1, Fibonacci.getFibonacci(1));
        Assert.assertEquals(1, Fibonacci.getFibonacci(1));
        Assert.assertEquals(63245986, Fibonacci.getFibonacci(39));
    }
}
