package com.zjluoyue.concurrency;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jia on 2017/2/8.
 * 一个银行出纳仿真
 * >>>>
 * 固定数量的服务器的负载大小的测试
 * {Args: 5}
 */

//只读对象不需要使用同步
class Customer {
    // 处理每个顾客花费的时间
    private final int serviceTime;

    public Customer(int tm) {
        serviceTime = tm;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public String toString() {
        return "[" + serviceTime + "]";
    }
}
// 顾客在等待某一个Teller服务所排列的一行
class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    public String toString() {
        if (this.size() == 0)
            return "[Empty]";
        StringBuilder result = new StringBuilder();
        for (Customer customer : this) {
            result.append(customer);
        }
        return result.toString();
    }
}
// 随机时间向队列中添加Customer
class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static Random rand = new Random(47);

    public CustomerGenerator(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                customers.put(new Customer(rand.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator terminating");
    }
}

/**
 * Teller从CustomerLine取走Customer，并且跟踪他服务顾客数量
 * 没有足够多的顾客时，他将会去执行doSomethingElse()
 * 出现许多的顾客时，他会去执行serveCustomerLine()
 * 由于实现了compareTo(), PriorityQueue将工作量最小的出纳员推向前台
 */
class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    // 服务过的人数，工作量的计算
    private int customerServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine cq) {
        customers = cq;
    }

    // 返回值为负优先级高
    @Override
    public synchronized int compareTo(Teller other) {
        return customerServed < other.customerServed ? -1 :
                (customerServed == other.customerServed ? 0 : 1);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 处理顾客的业务，需要花费一个随机时间
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {
                    customerServed++;
                    //空闲的出纳将一直被阻塞
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    public synchronized void doSomethingElse() {
        customerServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        assert !servingCustomerLine:"already serving: " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    public String toString() {
        return "Teller " + id + " ";
    }

    public String shortString() {
        return "T" + id;
    }
}

class TellerManager implements Runnable {
    private ExecutorService exec;
    private CustomerLine customers;
    // 工作的出纳基于优先级堆的无界优先级队列
    private PriorityQueue<Teller> workingTellers =
            new PriorityQueue<>();
    // 其他闲着的出纳
    private Queue<Teller> tellersDoingOtherThings =
            new LinkedList<>();

    private int adjustmentPeriod;
    private static Random rand = new Random(47);

    public TellerManager(ExecutorService e, CustomerLine customers, int adjustmentPeriod) {
        exec = e;
        this.customers = customers;
        this.adjustmentPeriod = adjustmentPeriod;
        Teller teller = new Teller(customers);
        // 出纳线程控制器
        exec.execute(teller);
        workingTellers.add(teller);
    }

    /**
     * 计算最优的出纳数量，可以加以改进
     */
    public void adjustTellerNumber() {
        // 顾客数量时出纳的两倍，首先去叫闲着的出纳，其次再去创建
        if (customers.size() / workingTellers.size() > 2) {
            if (tellersDoingOtherThings.size() > 0) {
                Teller teller = tellersDoingOtherThings.remove();
                // 出纳被唤醒
                teller.serveCustomerLine();
                //将指定的元素插入此优先级队列
                workingTellers.offer(teller);
                return;
            }
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.offer(teller);
            return;
        }
        // 将多余的出纳空闲出来
        if (workingTellers.size() > 1 &&
                customers.size() / workingTellers.size() < 2)
            reassignOneTeller();
        if (customers.size() == 0)
            while (workingTellers.size() > 1)
                reassignOneTeller();
    }

    /**
     * 多的出纳去doSomethingElse()
     */
    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.offer(teller);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                //计算时间
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                // 算出最优的出纳数量
                adjustTellerNumber();
                System.out.print(customers + " { ");
                for (Teller teller : workingTellers) {
                    System.out.print(teller.shortString() + " ");
                }
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + "interrupted");
        }
        System.out.println(this + "terminating");
    }

    public String toString() {
        return "TellerManager ";
    }
}
public class BankTellerSimulation {
    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        // 队伍太长顾客会离开
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(exec, customers, ADJUSTMENT_PERIOD));
        if (args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
