package com.zjluoyue.offer;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zjluoyue on 2017/1/13.
 */
public class JumpFloorTest {
    @Test
    public void jumpNumberTest() {
        Assert.assertEquals(1, JumpFloor.jumpNumber(0));
        Assert.assertEquals(1, JumpFloor.jumpNumber(1));
        Assert.assertEquals(2, JumpFloor.jumpNumber(2));
        Assert.assertEquals(3, JumpFloor.jumpNumber(3));
    }

    @Test
    public void jumpFloorIetr() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + ": " + JumpFloor.jumpFloorIetr(i));
        }
    }

}
