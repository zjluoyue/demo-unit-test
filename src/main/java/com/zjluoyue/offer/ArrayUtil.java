package com.zjluoyue.offer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by Jia on 2017/3/12.
 */
public class ArrayUtil {
    public static int MoreThanHalfNum_Solution(int [] array) {

        if (array == null)
            return 0;
        int length = array.length;
        int start = 0;
        int end = length-1;
        int middle = length >> 1;
        int index = partition(array, length, start, end);
        while (index != middle) {
            if (index > middle) {
                end = index - 1;
                index = partition(array, length, start, end);
            } else {
                start = index + 1;
                index = partition(array, length, start, end);
            }
        }
        int result = array[middle];

        if (!checkMoreThanHalf(array, result)) {
            return 0;
        }
        return result;
    }
    // 检查
    public static boolean checkMoreThanHalf(int[] array, int result) {
        int times = 0;
        for (int i : array) {
            if (i == result)
                times++;
        }
        if (times * 2 <= array.length)
            return false;
        return true;
    }
    // 快速排序
    public static int partition(int[] array, int length, int start, int end) {
        if (array == null || length <= 0 || start < 0 || end >= length)
            throw new RuntimeException("Invalid Parameters");
        // 随机选择
        int index = new Random().nextInt(end-start+1) + start;
        swap(array, end, index);

        int small = start - 1;
        for (index = start; index < end; index++) {
            if (array[index] < array[end]) {
                ++small;
                swap(array, index, small);
            }
        }
        ++small;
        swap(array, small, end);
        return small;
    }

    public static void swap(int[] array, int m, int n) {
        int temp = array[m];
        array[m] = array[n];
        array[n] = temp;
    }

    public static int moreThanHalfNum(int[] array) {
        if (array == null)
            return 0;
        int result = array[0];
        int times = 1;
        int len = array.length;
        for (int i = 0; i < len; i++) {
            if (times == 0) {
                result = array[i];
                times = 1;
            } else if (result == array[i]) {
                times++;
            } else {
                times--;
            }
        }
        if (!checkMoreThanHalf(array, result))
            return 0;
        return result;
    }
    // O(n)
    public static ArrayList<Integer> GetLeastNumbers(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        int length;
        if (input == null || (length = input.length) <= 0
                || k > length || k <= 0)
            return result;
        int start = 0;
        int end = length - 1;
        int index = partition(input, length, start, end);
        while (index != k-1) {
            if (index > k - 1) {
                end = index - 1;
                index = partition(input, length, start, end);
            } else {
                start = index + 1;
                index = partition(input, length, start, end);
            }
        }

        for (int i = 0; i < k; i++) {
            result.add(input[i]);
        }
        return result;
    }

    public static ArrayList<Integer> GetLeastNumbers1(int[] array, int k) {

        // 1.8 的构造方法
        PriorityQueue<Integer> ak = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer m, Integer n) {
                return m > n ? -1: (m < n ? 1: 0);
            }
        });
        ArrayList<Integer> result = new ArrayList<>();
        int len;
        if (array == null || (len = array.length) <= 0
                || k <= 0 || k > len)
            return result;

        for (int i = 0; i < len; i++) {
            if (i < k) {
                ak.offer(array[i]);
            } else {
                if (ak.peek() > array[i]) {
                    ak.poll();
                    ak.offer(array[i]);
                }
            }
        }
        while (!ak.isEmpty()) {
            result.add(ak.poll());
        }
        return result;
    }

    public static int FindGreatestSumOfSubArray(int[] array) {
        int sum = 0x80000000;
        int len;
        if (array == null || (len = array.length) < 1)
            return sum;
        int currentSum = 0;
        for (int i = 0; i < len; i++) {
            if (currentSum < 0)
                currentSum = array[i];
            else
                currentSum += array[i];

            if (currentSum > sum)
                sum = currentSum;
        }
        return sum;
    }

    public String PrintMinNumber(int [] numbers) {

        return "";
    }
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 2, 2, 2, 5, 4, 2};
        int[] a = {2, 2, 2, 3, 5, 3, 4, 1};
        System.out.println(MoreThanHalfNum_Solution(array));
        System.out.println(moreThanHalfNum(a));
        System.out.println(GetLeastNumbers(array, 4).toString());
        System.out.println(GetLeastNumbers(array, 0).toString());
        System.out.println(GetLeastNumbers1(array, 0).toString());
    }
}
