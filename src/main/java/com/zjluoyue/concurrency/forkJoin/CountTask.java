package com.zjluoyue.concurrency.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Jia on 17/3/2.
 */
public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) <= THRESHOLD) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (end + start) / 2;
            CountTask left = new CountTask(start, middle);
            CountTask right = new CountTask(middle+1, end);
            left.fork();
            right.fork();

            int leftSum = left.join();
            int rightSum = right.join();
            sum = leftSum + rightSum;
        }
        return sum;
    }

    public static void main(String[] args) throws Exception{
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1, 100);
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }
}
