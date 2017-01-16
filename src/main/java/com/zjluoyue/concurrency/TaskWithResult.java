package com.zjluoyue.concurrency;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by Jia on 2017/1/15.
 */
public class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }
    public String call() throws Exception {
        return "result of TaskWithResult " + id;
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        ArrayList<Future<String>> result = new ArrayList<Future<String>>();

        for (int i = 0; i < 10; i++) {
            result.add(exec.submit(new TaskWithResult(i)));
        }
        for (Future<String> fs : result) {
            try{
                //get() blocks until completion:
                System.out.println(fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                exec.shutdown();
            }
        }
    }
}
