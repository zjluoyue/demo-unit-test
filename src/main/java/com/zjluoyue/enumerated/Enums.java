package com.zjluoyue.enumerated;

import java.util.Random;

/**
 * Created by Jia on 2017/2/8.
 * 泛型方法: 将泛型参数列表置于返回值之前
 * 解决了不是泛型类而可以拥有泛型方法问题
 */
public class Enums {
    private static Random rand = new Random(47);

    public static <T extends Enum<T>> T random(Class<T> ec) {
        return random(ec.getEnumConstants());
    }

    public static <T> T random(T[] values) {
        return values[rand.nextInt(values.length)];
    }
 }
