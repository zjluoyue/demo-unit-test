package com.zjluoyue;


import java.util.ArrayList;
import java.util.List;

public class VarargType {

     public static void printArray(Object[] args) {
        for (Object obj: args) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    public static void f(Object... args) {
        for (Object obj : args) {
            System.out.print(obj + " ");
        }
        System.out.println();
    }


    public static String preMonth(String date, int pre) {
        String[] ymd = date.split("-");
        int m = Integer.parseInt(ymd[1]) - pre;
        int y = Integer.parseInt(ymd[0]);
        String preMonthData = "";
        if (m <= 0 ) {
            m += 12;
            y -= 1;
            preMonthData = preMonthData + y + m;
        } else if (m > 0 && m < 10) {
            preMonthData = preMonthData + y + "0" + m;
        } else {
            preMonthData = preMonthData + y + m;
        }
        return preMonthData;
    }

    public static void main(String[] args) {
        List<String> b= new ArrayList<String>();

        System.out.println(b.isEmpty());
        printArray(new Object[] {"one", "two", "three"});
        printArray(new Object[] {new Integer(1), new Integer(2),
                                 new Integer(3), new Integer(4),
                                 new Integer(5)});

        f("one", "two", "three");
        f(1, 2, 3, 4);
        System.out.println(preMonth("2016-10", 0));
        String[] a = new String[0];
        System.out.println(a);


    }
}
