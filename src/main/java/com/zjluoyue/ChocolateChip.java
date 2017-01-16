package com.zjluoyue;

import com.zjluoyue.dessert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zjluoyue on 2016/8/28.
 */
public class ChocolateChip extends Cookie {


    public ChocolateChip() {
        System.out.println("ChocolateChip constructor");
    }

    public void chomp() {
      // bite();
    }

    public static void main(String[] args) {
        ChocolateChip x = new ChocolateChip();
        x.chomp();

        ArrayList<String> strList = new ArrayList<String>();
        strList.add("q");
        //类型转换错误
//        String[] strings = (String[]) strList.toArray();

        //指定类型数组给予 ArrayList 到原生数组的转换
        String[] a = {"1", "2", "3"};
        String[] str =  strList.toArray(a);
        System.out.println(a[0]+ a[1] + a[2] + " " + strList.size() + str[0] + str[1] + str[2]);
    }
}
