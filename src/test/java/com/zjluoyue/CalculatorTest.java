package com.zjluoyue;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * 测试类必须要继承于TestCase父类
 *
 * 在junit 3.8中，测试方法需要满足如下原则：
 *
 * 1. public的
 * 2. void的
 * 3. 无方法参数
 * 4. 方法名称必须以test开头
 */
public class CalculatorTest extends TestCase {

    private Calculator cal;

    @Override
    protected void setUp() throws Exception {
        cal = new Calculator();
    }

    @Override
    protected void tearDown() throws Exception {

    }

    public void testAdd() {
        int result = cal.add(1, 2);
        //前面是期望结果，后面是实际执行结果
        Assert.assertEquals(3, result);
    }

    public void testSubtract() {
        int result = cal.subtract(4, 3);
        Assert.assertEquals(1, result);
    }

    public void testMultiply() {
        int result = cal.multiply(2, 3);

        Assert.assertEquals(6, result);
    }

    public void testDivide() {
        int result = 0;

        try {
            result = cal.divide(6, 2);
        }
        catch (Exception e) {
            e.printStackTrace();

            Assert.fail();
        }

        Assert.assertEquals(3, result);
    }

    public void testDivideDivideByZero() {
        Throwable tx = null;

        try {
            cal.divide(6, 0);
            Assert.fail("测试失败");
        }
        catch(Exception ex) {
            tx = ex;
        }

        Assert.assertEquals(Exception.class, tx.getClass());
        Assert.assertEquals("除数不能为0", tx.getMessage());
    }
}
