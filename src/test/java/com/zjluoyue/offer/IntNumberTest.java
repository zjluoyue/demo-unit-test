package com.zjluoyue.offer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zjluoyue on 2017/1/12.
 */
public class IntNumberTest {
    @Test
    public void getFibonacciTest() {
//        for (int i = 0; i <= 39; i++) {
//            System.out.println(i + ": " + Fibonacci.getFibonacci(i));
//        }

        Assert.assertEquals(0, IntNumber.getFibonacci(0));
        Assert.assertEquals(1, IntNumber.getFibonacci(1));
        Assert.assertEquals(1, IntNumber.getFibonacci(1));
        Assert.assertEquals(63245986, IntNumber.getFibonacci(39));
    }
}
