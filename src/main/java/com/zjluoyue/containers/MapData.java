package com.zjluoyue.containers;

import com.zjluoyue.generics.Generator;

import java.util.LinkedHashMap;

/**
 * Created by Jia on 2017/2/10.
 */
public class MapData<K, V> extends LinkedHashMap<K, V> {
    public MapData(Generator<Pair<K, V>> gen, int quantity) {
        for (int i = 0; i < quantity; i++) {
            Pair<K, V> p = gen.next();
            put(p.key, p.value);
        }
    }

    public MapData(Generator<K> genK, Generator<V> genV, int quantity) {
        for (int i = 0; i < quantity; i++) {
            put(genK.next(), genV.next());
        }
    }

    public static <K, V> MapData<K, V>
    map(Generator<K> genK, Generator<V> genV, int quantity) {
        return new MapData<K, V>(genK, genV, quantity);
    }
}
