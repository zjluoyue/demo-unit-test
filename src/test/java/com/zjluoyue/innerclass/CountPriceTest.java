package com.zjluoyue.innerclass;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.zjluoyue.innerclass.CountPrice.countTotal;

/**
 * Created by zjluoyue on 2016/9/17.
 */
public class CountPriceTest {

    private Price price;
    
    @Before
    public void setUp() {
        price = new Price(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6);
    }

    @Test
    public void TestCountTotal() {
        int total = 0;
        try {
            total = countTotal(price, 60);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(210, total);
    }
    @After
    public void tearDown() {
        
    }

}
