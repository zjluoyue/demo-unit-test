package com.zjluoyue.typeinfo;

import java.lang.reflect.Constructor;
import java.util.Random;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class ClassInitialization {
    public static Random rand = new Random(47);

    public static void main(String[] args) throws ClassNotFoundException {
        Class initable = Initable.class;
        System.out.println("After creating Initable ref");
        //does not trigger initalization 不触发初始化
        System.out.println(Initable.staticFinal);
        //does trigger initalization 初始化被触发
        System.out.println(Initable.staticFinal2);
        //does trigger initalization
        System.out.println(Initable2.staticNonFinal);
        //does trigger initalization 初始化被触发
        Class initable3 = Class.forName("com.zjluoyue.typeinfo.Initable3");
        System.out.println("After creating Initable3 ref");
        System.out.println(Initable3.staticNonFinal);

        Class<?> c = CountedInteger.class;
        Constructor[] ctors = c.getConstructors();

        for (Constructor ctor : ctors) {
            System.out.println(ctor);
        }
    }

    static class CountedInteger {
        private static long counter;
        private final long id = counter++;

        public String toString() {
            return Long.toString(id);
        }
    }
}
