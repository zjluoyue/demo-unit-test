package com.zjluoyue.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Jia on 17/2/16.
 */
public class CollectionMethods {
    public static void main(String[] args) {

        Collection<Integer> integers = Arrays.asList(1, 2, 3);
        ArrayList<String> a = new ArrayList<>();
        a.add("marry");
        a.add("sb");
        Collection<String> c = new ArrayList<>();
        c.addAll(a);
        System.out.println(c);
        // 固定数组，不可以使用更改操作
        try {
            integers.addAll(Arrays.asList(4, 5, 6));
        } catch (UnsupportedOperationException e) {

        }
        Object[] array = c.toArray();

        String[] str = c.toArray(new String[4]);
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(str));
        c.removeAll(a);

    }
}
