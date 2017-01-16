package com.zjluoyue.innerclass;

/**
 * Created by zjluoyue on 2016/9/18.
 */
public class Caller {

    private Increment callbackReference;

    Caller(Increment cbh) {
        callbackReference = cbh;
    }

    void go() {
        callbackReference.increment();
    }

    public static void main(String[] args) {
        Callee c = new Callee();
        MyIncrement.f(c);
        Caller caller = new Caller(c.getCallBackReference());
        caller.go();
    }
}
