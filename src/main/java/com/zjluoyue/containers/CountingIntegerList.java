package com.zjluoyue.containers;

import java.util.AbstractList;

/**
 * Created by Jia on 2017/2/9.
 */
public class CountingIntegerList extends AbstractList<Integer> {
    private int size;

    public CountingIntegerList(int size) {
        this.size = size < 0 ? 0: size;
    }

    @Override
    public Integer get(int index) {
        return Integer.valueOf(index);
    }

    @Override
    public int size() {
        return size;
    }

//    public static void main(String[] args) {
//        System.out.println(new CountingIntegerList(30));
//    }
}
