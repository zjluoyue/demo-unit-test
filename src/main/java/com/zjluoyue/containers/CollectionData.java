package com.zjluoyue.containers;

import com.zjluoyue.generics.Generator;

import java.util.ArrayList;

/**
 * Created by Jia on 2017/2/9.
 * 适配器设计模式
 */
public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            add(gen.next());
        }
    }

    public static <T> CollectionData<T>
    list(Generator<T> gen, int quantity) {
        return new CollectionData<T>(gen, quantity);
    }
}
