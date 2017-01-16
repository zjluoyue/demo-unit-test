package com.zjluoyue.typeinfo.reflect;

import com.zjluoyue.typeinfo.ClassInitialization;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class ShowMethods {
    private static String usage =
            "usage:\n" +
            "ShowMethods qualified.class.name\n" +
            "To show all methods in class or:\n" +
            "ShowMethods qualified.class.name word\n" +
            "To search for methods involving 'word'";
    private static Pattern p = Pattern.compile("\\w+\\.");

    public static void main(String[] args) {
        int lines = 0;
        try {
            Class<?> c =  Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor[] ctors = c.getConstructors();
            if (args.length == 1) {
                for (Method method: methods) {
                    System.out.println(p.matcher(method.toString()).replaceAll(""));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
