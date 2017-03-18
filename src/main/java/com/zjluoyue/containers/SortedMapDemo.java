package com.zjluoyue.containers;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Jia on 17/2/17.
 */
public class SortedMapDemo {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> sortedMap =
                new TreeMap<>();
        for (int i = 0; i < 10; i++) {
            sortedMap.put(i, i);
        }
        System.out.println(sortedMap);
        Integer low = sortedMap.firstKey();
        Integer high = sortedMap.lastKey();
        Iterator<Integer> it = sortedMap.keySet().iterator();
        for (int i = 0; i <= 6; i++) {
            if (i == 3) low = it.next();
            if (i == 6) high = it.next();
            else it.next();
        }
        System.out.println(low);
        System.out.println(high);
    }
}
