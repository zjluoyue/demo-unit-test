package com.zjluoyue.myregex;

/**
 * Created by Jia on 17/3/1.
 */
public class BreakDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((i & 0x1) == 0) {
                    System.out.println("i = " + i);
                    break;
                }
            }
        }
    }
}
