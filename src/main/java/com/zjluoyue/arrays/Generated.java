package com.zjluoyue.arrays;

import com.zjluoyue.containers.CollectionData;
import com.zjluoyue.generics.Generator;


/**
 * Created by Jia on 2017/2/9.
 */
public class Generated {
    public static <T> T[] array(T[] a, Generator<T> gen) {
        return new CollectionData<T>(gen, a.length).toArray(a);
    }

    public static <T> T[] array(Class<T> type, Generator<T> gen, int size) {
        T[] a = (T[]) java.lang.reflect.Array.newInstance(type, size);
        return new CollectionData<T>(gen, size).toArray(a);
    }
}
