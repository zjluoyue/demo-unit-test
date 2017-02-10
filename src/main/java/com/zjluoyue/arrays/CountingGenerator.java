package com.zjluoyue.arrays;

import com.zjluoyue.generics.Generator;

import javax.swing.text.StyledEditorKit;

/**
 * Created by Jia on 2017/2/10.
 */
public class CountingGenerator {
    public static class Boolean implements Generator<java.lang.Boolean> {
        private boolean value = false;

        public java.lang.Boolean next() {
            value = !value;
            return value;
        }
    }

    public static class Integer implements Generator<java.lang.Integer> {
        private int value = 0;
        @Override
        public java.lang.Integer next() {
            return value++;
        }
    }
}
