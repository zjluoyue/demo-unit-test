package com.zjluoyue.innerclass;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by zjluoyue on 2016/9/17.
 */
public class CountPrice {
    static int countTotal(Price price, int num)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        String pr = "price";
        String ct = "count";
        Map<String, Object> priceMap = Price.convertBean(price);

        int total = 0;
        int i = 6;
        while (i > 1) {
            int count =(Integer) priceMap.get(ct + (i-1));
            int p = (Integer) priceMap.get(pr + i);

            if (num >= count) {
                total += (num-count) * p;
                num = count;
            }
            i--;
        }

            total += num * (Integer) priceMap.get(pr + i);


        return total;
    }

    public static void main(String[] args)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        Price price = new Price(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6);
//        Price price = new Price();
        for (int i = 0; i <= 60 ; i++) {
            int total = countTotal(price, i);
            System.out.println(i + ": " + total);
        }


//        int total = countTotal(price, 24);
//        System.out.println(total);
    }
}
