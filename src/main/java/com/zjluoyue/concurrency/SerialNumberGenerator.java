package com.zjluoyue.concurrency;

/**
 * Created by Jia on 2017/1/17.
 */
public class SerialNumberGenerator {
    private static volatile int serialNumber = 0;

    /**
     * 多个线程竞争后还是会产生相同的序列数，解决方法是synchronized
     * @return
     */
    public static int nextSerialNumber() {
        return serialNumber++;
    }
}
