package com.zjluoyue.typeinfo.factory;

/**
 * Created by zjluoyue on 2016/10/14.
 */
public class RegisteredFactory {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            System.out.println(Part.create());
        }
    }
}
