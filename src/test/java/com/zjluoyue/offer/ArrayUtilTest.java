package com.zjluoyue.offer;

import org.junit.Test;

import static com.zjluoyue.offer.ArrayUtil.GetLeastNumbers;

/**
 * Created by Jia on 2017/3/10.
 */
public class ArrayUtilTest {
    @Test
    public void testPermutation() {

    }

    @Test
    public void testGetLeastNumbers() {
        int[] array = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        int[] a = {2, 2, 2, 3, 5, 3, 4, 1};
        System.out.println(GetLeastNumbers(array, 4).toString());
        System.out.println(GetLeastNumbers(array, 1).toString());
        System.out.println(GetLeastNumbers(null, 1).toString());
        System.out.println(GetLeastNumbers(null, 0).toString());
    }
}
