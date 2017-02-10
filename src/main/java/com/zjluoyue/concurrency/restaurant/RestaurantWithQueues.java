package com.zjluoyue.concurrency.restaurant;

import com.zjluoyue.enumerated.menu.Course;
import com.zjluoyue.enumerated.menu.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Jia on 2017/2/8.
 * 饭店仿真
 * {Args: 5}
 * SynchronousQueue一种阻塞队列，其中每个插入操作必须等待另一个线程的对应移除操作 ，反之亦然。
 * 同步队列没有任何内部容量，甚至连一个队列的容量都没有
 * 任务间通信经由队列相互发送
 */
class Order {
    private static int counter = 0;
    private final int id = counter++;
    private final Customer customer;
    private final WaitPerson waitPerson;
    private final Food food;

    public Order(Customer cust, WaitPerson wp, Food f) {
        customer = cust;
        waitPerson = wp;
        food = f;
    }

    public Food item() {
        return food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    public String toString() {
        return "Order: " + id + " item: " + food +
                " for: " + customer +
                " served by: " + waitPerson;
    }
}

class Plate {
    private final Order order;
    private final Food food;

    public Plate(Order ord, Food f) {
        order = ord;
        food = f;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return food.toString();
    }
}
// 顾客
class Customer implements Runnable{
    private static int counter = 0;
    private final int id = counter++;
    private final WaitPerson waitPerson;
    private SynchronousQueue<Plate> placeSetting =
            new SynchronousQueue<>();

    public Customer(WaitPerson waitPerson) {
        this.waitPerson = waitPerson;
    }

    public void deliver(Plate p) throws InterruptedException {
        placeSetting.put(p);
    }

    @Override
    public void run() {
        // 正常情况下每个顾客将会选择四种食物，然后结束就餐
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                // 下单
                waitPerson.placeOrder(this, food);
                // 吃饭之前会等待put，SynchronousQueue会阻塞
                System.out.println(this + "eating " + placeSetting.take());
            } catch (InterruptedException e) {
                System.out.println(this + "waiting for " + course + " interrupted");
                break;
            }
        }
        System.out.println(this + "finished meal, leaving");
    }

    @Override
    public String toString() {
        return "Customer " + id + " ";
    }
}
// 服务员
class WaitPerson implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    // 做好的菜
    BlockingQueue<Plate> filledOrders =
            new LinkedBlockingQueue<>();
    public WaitPerson(Restaurant rest) {
        restaurant = rest;
    }

    public void placeOrder(Customer cust, Food food) {
        try {
            restaurant.orders.put(new Order(cust, this, food));
        } catch (InterruptedException e) {
            System.out.println(this + " placeOrder interrupted");
        }
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Plate plate = filledOrders.take();
                System.out.println(this + "received " + plate +
                    " delivering to " + plate.getOrder().getCustomer());
                // 服务员上菜
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " off duty");
    }

    @Override
    public String toString() {
        return "WaitPerson " + id + " ";
    }
}
// 厨师
class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    private static Random rand = new Random(47);
    public Chef(Restaurant rest) {
        restaurant = rest;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 拿到菜单
                Order order = restaurant.orders.take();
                Food requestedItem = order.item();
                // 制作花费一定时间
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order, requestedItem);
                // 叫来服务员上菜
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " off duty");
    }

    @Override
    public String toString() {
        return "Chef " + id + " ";
    }
}

class Restaurant implements Runnable {
    // 服务员队列
    private List<WaitPerson> waitPersons =
            new ArrayList<>();
    // 厨师队列
    private List<Chef> chefs = new ArrayList<>();
    private ExecutorService exec;
    private static Random rand = new Random(47);
    // 订单
    BlockingQueue<Order> orders =
            new LinkedBlockingQueue<>();

    public Restaurant(ExecutorService e, int nWaitPersons, int nChefs) {
        exec = e;
        // 服务员线程
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        // 厨师线程
        for (int i = 0; i < nChefs; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 服务员随机服务一个顾客
                WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
                Customer c = new Customer(wp);
                exec.execute(c);
                // 间隔100ms，就会进来一个顾客
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant interrupted");
        }
        System.out.println("Restaurant closing");
    }
}
public class RestaurantWithQueues {
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec, 5, 2);
        exec.execute(restaurant);
        if (args.length > 0)
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }
}
