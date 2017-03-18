package com.zjluoyue.offer;

import java.util.Arrays;

/**
 * Created by Jia on 2017/1/12.
 */
public class IntNumber {
    // 斐波拉契数列
    public static int getFibonacci(int n) {
        int a = 0, b = 1;
        for (int i = 0; i < n; i++) {
            b += a;
            a = b - a;
        }
        return a;
    }
    // 从一到N整数中1的次数
    public static int NumberOf1Between1AndN(int n) {
        if (n <= 0)
            return 0;
        char[] nChar = String.valueOf(n).toCharArray();

        return NumberOf1(nChar, 0);
    }
    //
    private static int NumberOf1(char[] nChar, int i) {
        int len;
        if (nChar == null || (len = nChar.length) <= 0 || i >= len)
            return 0;
        // 一位数1的个数计算
        int first = nChar[i] - '0';
        // 0，返回计算为零
        if (i == len-1 && first == 0)
            return 0;
        // 1-9，只有一个1
        if (i == len-1 && first > 0)
            return 1;
        //
        int numFirstDigit = 0;
        if (first > 1)
            numFirstDigit = powerBase10(len - 1 - i);
        // 首位为1，个数出现的次数只有剩下次数加一次
        else if (first == 1)
            numFirstDigit = Integer.valueOf(String.valueOf(
                    Arrays.copyOfRange(nChar, i+1, len))) + 1;

        int numOtherDigits = first * (len - 1 - i) * powerBase10(len - 2 - i);

        int numRecursive = NumberOf1(nChar, i + 1);
        return numFirstDigit + numOtherDigits + numRecursive;
    }


    private static int powerBase10(int n) {
        int result = 1;
        for (int i = 0; i < n; i++) {
            result *= 10;
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println(NumberOf1Between1AndN(12345));
        System.out.println(NumberOf1Between1AndN(0));
        System.out.println(NumberOf1Between1AndN(1));
        System.out.println(NumberOf1Between1AndN(11));
        System.out.println(NumberOf1Between1AndN(46));
    }
}
