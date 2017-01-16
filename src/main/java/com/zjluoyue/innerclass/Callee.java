package com.zjluoyue.innerclass;

/**
 * Created by zjluoyue on 2016/9/18.
 */
public class Callee extends MyIncrement {

    private int i = 0;

    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }

    private class Closure implements Increment {

        public void increment() {
            Callee.this.increment();
        }
    }

    Increment getCallBackReference() {
        return new Closure();
    }
}
