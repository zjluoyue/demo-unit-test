package com.zjluoyue.offer;

/**
 * Created by zjluoyue on 2017/1/13.
 */
public class JumpFloor {
    public static int jumpNumber(int target) {
        //代表最后只剩一个台阶时，只有一种方案
        if (target <= 0) {
            return 0;
        } else if (target == 1 ) {
            return 1;
        } else if (target == 2) {
            return 2;
        }
        return jumpNumber(target - 1) + jumpNumber(target - 2);
    }

    public static int jumpFloorIetr(int target) {
        int f1 = 0;
        int f2 = 1;
        for (int i = 1; i <= target; i++) {
            f2 = f1 + f2;
            f1 = f2 - f1;
        }
        return target == 0? f1: f2;
    }
}
