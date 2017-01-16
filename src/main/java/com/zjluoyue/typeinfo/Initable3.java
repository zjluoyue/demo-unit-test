package com.zjluoyue.typeinfo;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class Initable3 {
    static int staticNonFinal = 74;
    static {
        System.out.println("Initializing Initable3");
    }
}
