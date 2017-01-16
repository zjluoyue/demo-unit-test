package com.zjluoyue;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zjluoyue on 2016/9/2.
 */
public class VarargTypeTest {

    @Test
    public void testPreMonth() {
        Assert.assertEquals("201511", VarargType.preMonth("2016-01", 2));
        Assert.assertEquals("201608", VarargType.preMonth("2016-10", 2));
        Assert.assertEquals("201512", VarargType.preMonth("2016-02", 2));
        Assert.assertEquals("201601", VarargType.preMonth("2016-03", 2));
        Assert.assertEquals("201612", VarargType.preMonth("2016-12", 0));
    }

}
