package com.zjluoyue.arrays;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by zjluoyue on 2017/1/14.
 */
public class ComparingArrays {
    public static void main(String[] args) {
        //数组的比较，equals()
        int a1[] = new int[10];
        int a2[] = new int[10];

        Arrays.fill(a1, 47);
        Arrays.fill(a2, 47);
        System.out.println(Arrays.equals(a1, a2));
        a2[3] = 11;
        System.out.println(Arrays.equals(a1, a2));
        String s1[] = new String[4];
        Arrays.fill(s1, "Hi");
        String s2[] = {new String("Hi"), new String("Hi"),
        new String("Hi"), new String("Hi")};
        System.out.println(Arrays.equals(s1, s2));

        //对象的比较，排序sort()
        int[] a = new int[10];
        Random r = new Random(47);
        for (int i = 0; i < a.length; i++) {
            a[i] = r.nextInt(100);
        }

        System.out.println(Arrays.toString(a));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));

        String s = "Hello world!";
        char[] str = s.toCharArray();
        System.out.println(Arrays.toString(str));
        Arrays.sort(str);
        System.out.println(Arrays.toString(str));
        System.out.println(String.valueOf(str));


    }
}
