package com.zjluoyue.innerclass;


import org.apache.commons.beanutils.BeanMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zjluoyue on 2016/9/17.
 */
public class Price {

    private int count0;
    private int price1;
    private int count1;
    private int price2;
    private int count2;
    private int price3;
    private int count3;
    private int price4;
    private int count4;
    private int price5;
    private int count5;
    private int price6;

    public Price() {
        this.count0 = 0;
    }

    public Price(int price1, int count1, int price2, int count2, int price3, int
            count3,
                 int price4, int count4, int price5, int count5, int price6) {
        this();
        this.price1 = price1;
        this.count1 = count1;
        this.price2 = price2;
        this.count2 = count2;
        this.price3 = price3;
        this.count3 = count3;
        this.price4 = price4;
        this.count4 = count4;
        this.price5 = price5;
        this.count5 = count5;
        this.price6 = price6;
    }

    public int getCount0() {
        return count0;
    }

    public void setCount0(int count0) {
        this.count0 = count0;
    }

    public int getPrice1() {
        return price1;
    }

    public void setPrice1(int price1) {
        this.price1 = price1;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getPrice2() {
        return price2;
    }

    public void setPrice2(int price2) {
        this.price2 = price2;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public int getPrice3() {
        return price3;
    }

    public void setPrice3(int price3) {
        this.price3 = price3;
    }

    public int getCount3() {
        return count3;
    }

    public void setCount3(int count3) {
        this.count3 = count3;
    }

    public int getPrice4() {
        return price4;
    }

    public void setPrice4(int price4) {
        this.price4 = price4;
    }

    public int getCount4() {
        return count4;
    }

    public void setCount4(int count4) {
        this.count4 = count4;
    }

    public int getPrice5() {
        return price5;
    }

    public void setPrice5(int price5) {
        this.price5 = price5;
    }

    public int getCount5() {
        return count5;
    }

    public void setCount5(int count5) {
        this.count5 = count5;
    }

    public int getPrice6() {
        return price6;
    }

    public void setPrice6(int price6) {
        this.price6 = price6;
    }

    /**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type, Object.class);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                Class aType = descriptor.getPropertyType();
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, 0.0);
                }

//                System.out.println(aType == String.class);
            }
        }
        return returnMap;
    }

    public static void main(String[] args)
            throws IllegalAccessException, IntrospectionException, InvocationTargetException, NoSuchFieldException {
        Price price = new Price();

        // price的实例化
        Class t = null;
        try {
            t = Class.forName("com.zjluoyue.innerclass.Price");
            t.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Price pr1 = new Price(1, 10, 2, 20, 3, 30, 4, 40, 5, 50, 6);
        Map pr = new BeanMap(pr1);

        System.out.println(pr.size());
        for (Object key : pr.keySet()) {
            System.out.println(key + ": " + pr.get(key));
        }
        // 只能访问公共 Field，私有无法访问
        //Field[] fields = price.getClass().getFields();

        // 所有已声明的字段，包括私有字段
        Field[] fields = price.getClass().getDeclaredFields();
        Field fl = price.getClass().getDeclaredField("count0");
        System.out.println("private int count0: " + fl.get(price));
//        Map fields = Price.convertBean(price);
        System.out.println(fields);
        System.out.println(fields.length);

        for (Object f: fields) {
            System.out.println(f + ": ");
        }
//        for (Object f: fields.keySet()) {
//            System.out.println(f + ": " + fields.get(f));
//        }

        int ia = 0;
    }
}
