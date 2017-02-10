package com.zjluoyue.concurrency.factory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by Jia on 2017/2/9.
 * 机器人组装线
 * car>>>底盘>>>发动机>>>车厢>>>轮子>>>car!!!
 * 加锁原则写加锁，读不加
 */
class Car {
    private final int id;
    private boolean
        engine = false, driveTrain = false, wheels = false;

    public Car(int idn) {
        id = idn;
    }

    public Car() {
        id = -1;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void addEngine() {
        engine = true;
    }

    public synchronized void addDriveTrain() {
        driveTrain = true;
    }

    public synchronized void addWheels() {
        wheels = true;
    }

    @Override
    public String toString() {
        return "Car " + id +
                " [ engine: " + engine +
                " driveTrain: " + driveTrain +
                " wheels: " + wheels + " ]";
    }
}

class CarQueue extends LinkedBlockingQueue<Car> {

}
// 底盘构建
class ChassisBuilder implements Runnable {
    private CarQueue carQueue;
    private int counter = 0;
    public ChassisBuilder(CarQueue cq) {
        carQueue = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(500);
                // 构建一台car的底盘
                Car c = new Car(counter++);
                System.out.println("ChassisBuilder created " + c);
                // 加入队列
                carQueue.put(c);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted: ChassisBuilder");
        }
        System.out.println("ChassisBuilder off");
    }
}

/**
 * CyclicBarrier
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点
 */
class Assembler implements Runnable {
    private CarQueue chassisQueue, finishingQueue;
    private Car car;
    private CyclicBarrier barrier = new CyclicBarrier(4);
    private RobotPool robotPool;
    public Assembler(CarQueue cq, CarQueue fq, RobotPool rp) {
        chassisQueue = cq;
        finishingQueue = fq;
        robotPool = rp;
    }

    public Car car() {
        return car;
    }

    public CyclicBarrier barrier() {
        return barrier;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 产品线
                car = chassisQueue.take();
                robotPool.hire(EngineRoot.class, this);
                robotPool.hire(DriveTrainRobot.class, this);
                robotPool.hire(WheelRobot.class, this);
                // 阻塞等待robot工作完成
                barrier.await();
                // 加入完成队列
                finishingQueue.put(car);
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting Assembler via interrupt");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Assembler off");
    }
}
// 产品线输出
class Reporter implements Runnable {
    private CarQueue carQueue;
    // finishingQueue
    public Reporter(CarQueue cq) {
        carQueue = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(carQueue.take());
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting Reporter via interrupt");
        }
        System.out.println("Reporter off");
    }
}
// 模板设计模式
abstract class Robot implements Runnable {
    private RobotPool pool;
    public Robot(RobotPool p) {
        pool = p;
    }
    protected Assembler assembler;

    public Robot assignAssembler(Assembler assembler) {
        this.assembler = assembler;
        return this;
    }
    // 雇佣状态
    private boolean engage = false;

    public synchronized void engage() {
        engage = true;
        notifyAll();
    }

    abstract protected void performService();

    public void run() {
        try {
            // 未雇佣将会wait(), 雇佣之后会执行，初始化状态
            powerDown();
            while (!Thread.interrupted()) {
                performService();
                // 同步屏障
                assembler.barrier().await();
                powerDown();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting " + this + " via interrupt");
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println(this + " off");
    }

    private synchronized void powerDown() throws InterruptedException {
        engage = false;
        assembler = null;
        // 回收线程，处于闲置状态
        pool.release(this);
        while (engage == false) {
            wait();
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
class EngineRoot extends Robot{

    public EngineRoot(RobotPool pool) {
        super(pool);
    }

    @Override
    protected void performService() {
        System.out.println(this + " installing engine");
        assembler.car().addEngine();
    }
}

class DriveTrainRobot extends Robot {
    public DriveTrainRobot(RobotPool pool) {
        super(pool);
    }

    @Override
    protected void performService() {
        System.out.println(this + " installing Drivetrain");
        assembler.car().addDriveTrain();
    }
}

class WheelRobot extends Robot {

    public WheelRobot(RobotPool pool) {
        super(pool);
    }
    @Override
    protected void performService() {
        System.out.println(this + " installing Wheels");
        assembler.car().addWheels();
    }
}
class RobotPool {
    private Set<Robot> pool = new HashSet<>();
    public synchronized void add(Robot r) {
        pool.add(r);
        notifyAll();
    }
    // 机器人线程的选择
    public synchronized void hire(
            Class<? extends Robot> robotType, Assembler d)
            throws InterruptedException {
        for (Robot r : pool) {
            if (r.getClass().equals(robotType)) {
                pool.remove(r);

                r.assignAssembler(d);
                //雇佣
                r.engage();
                return;
            }
        }
        // 等待合适的robot回收至线程池中
        wait();
        hire(robotType, d);
    }
    // 回收线程至线程池
    public synchronized void release(Robot r) {
        add(r);
    }
}
public class CarBuilder {
    public static void main(String[] args) throws InterruptedException {
        CarQueue chassisQueue = new CarQueue(),
                 finishingQueue = new CarQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        // robot线程池管理
        RobotPool robotPool = new RobotPool();
        exec.execute(new EngineRoot(robotPool));
        exec.execute(new DriveTrainRobot(robotPool));
        exec.execute(new WheelRobot(robotPool));
        // 产品线构造
        exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
        // 产品线输出
        exec.execute(new Reporter(finishingQueue));
        // 底盘
        exec.execute(new ChassisBuilder(chassisQueue));
        TimeUnit.SECONDS.sleep(7);
        exec.shutdownNow();
    }

}
