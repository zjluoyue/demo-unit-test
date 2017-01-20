package com.zjluoyue.concurrency;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/1/18.
 */
class NIOBlocked implements Runnable {
    private final SocketChannel sc;

    private final int id;

    public NIOBlocked(SocketChannel sc, int idn) {
        this.sc = sc;
        this.id = idn;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read() in id: " + id + this);
            sc.read(ByteBuffer.allocate(1));
        } catch (ClosedByInterruptException e) {
            // 当在某个信道的 I/O 操作中处于阻塞状态的某个线程被另一个线程中断时
            // 该线程将收到此经过检查的异常。抛出此异常前将关闭该信道，并设置先前处于阻塞状态的线程的中断状态。
            System.out.println("ClosedByInterruptException");
        } catch (AsynchronousCloseException e) {
            // 当在某个信道的 I/O 操作中处于阻塞状态的某个线程被另一个线程关闭了该信道或部分信道时
            // 该信道所接收的经过检查的异常
            System.out.println("AsynchronousCloseException");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 可中断阻塞能执行下列代码
        System.out.println("Exiting NIOBlocked,run() " + this);
    }
}
public class NIOInterruption {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(10000);
        InetSocketAddress isa = new InetSocketAddress("localhost", 10000);
        SocketChannel sc1 = SocketChannel.open(isa);
        SocketChannel sc2 = SocketChannel.open(isa);
        Future<?> f = exec.submit(new NIOBlocked(sc1, 1));
        exec.execute(new NIOBlocked(sc2, 2));
        exec.shutdown();
        TimeUnit.SECONDS.sleep(1);
        // produce an interrupt via cancel:
        f.cancel(true);
        TimeUnit.SECONDS.sleep(1);
        // 由于shutdown() 该线程并不能立即结束，即锁未被释放
        // 关闭底层资源才能释放，或是使用shutdownNow()方法来终止
        sc2.close();
    }
}
