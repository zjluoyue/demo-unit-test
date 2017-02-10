package com.zjluoyue.arrays;

import com.zjluoyue.generics.Generator;

import java.util.Random;

/**
 * Created by Jia on 2017/2/9.
 */
public class RandomGenerator {
    private static Random rand = new Random(47);

    public static class Boolean implements Generator<java.lang.Boolean> {
        public java.lang.Boolean next() {
            return rand.nextBoolean();
        }
    }

    public static class Integer implements Generator<java.lang.Integer> {
        private int mod = 10000;
        public Integer() {}
        public Integer(int modulo) {
            mod = modulo;
        }
        @Override
        public java.lang.Integer next() {
            return rand.nextInt(mod);
        }
    }

}
