package com.zjluoyue.io;

/**
 * Created by Jia on 2017/3/20.
 */
public class OSExecuteDemo {
    public static void main(String[] args) {
        OSExecute.command("javap target/classes/com/zjluoyue/io/OSExecuteDemo" +
                ".class");
    }
}
