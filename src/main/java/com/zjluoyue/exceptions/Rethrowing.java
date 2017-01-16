package com.zjluoyue.exceptions;

/**
 * Created by zjluoyue on 2016/12/17.
 */
public class Rethrowing {

    public static void f() throws Exception {
        System.out.println("originating the exception in f()");
        throw new RuntimeException("Fsfsfsdf");
    }

    public static String g() throws Exception {
        try {
            f();
        } catch (Exception e) {
            System.out.println("Inside g(), e.printStackTrace()");
            e.printStackTrace(System.out);
//            return "error";
            throw e;

        }finally {
            System.out.println("excute?");
            return "OK";
        }

    }

    public static void main(String[] args) {

        try {
//            System.out.println(g());
            g();

        } catch (Exception e) {
            System.out.println("main: printStackTrace()");
            e.printStackTrace(System.out);
        }
    }
}
