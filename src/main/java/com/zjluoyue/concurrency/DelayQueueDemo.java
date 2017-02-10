package com.zjluoyue.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * Created by Jia on 2017/2/6.
 * 无界BlockingQueue
 */
class DelayedTask implements Runnable, Delayed {

    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence =
            new ArrayList<>();

    public DelayedTask(int delayInMilliseconds) {
        this.delta = delayInMilliseconds;
        trigger = System.nanoTime() +
                NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed arg) {
        DelayedTask that = (DelayedTask) arg;
        if (trigger < that.trigger) return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }

    public String toString() {
        return String.format("[%1$-4d]", delta) +
                " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;
        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }

        public void run() {
            for (DelayedTask pt : sequence) {
                System.out.print(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }
    @Override
    public void run() {
        try {
            int i = 0;
            while (!Thread.interrupted()) {
                //获取并移除此队列的头部，在可从此队列获得到期延迟的元素之前一直等待
                q.take().run();
                System.out.println(i);
                i++;
            }
        } catch (InterruptedException e) {

        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}
public class DelayQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        // 将任务加入队列中
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(rand.nextInt(5000)));
        }

        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }
}
