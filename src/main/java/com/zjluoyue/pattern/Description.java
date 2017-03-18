package com.zjluoyue.pattern;

/**
 * Created by Jia on 2017/3/14.
 * 装饰模式的典型例子I/O类
 */
abstract class Beverage {
    String description = "Unknown Beverage";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }
    @Override
    public double cost() {
        return 1.99;
    }
}
class HouseBlend extends Beverage{

    public HouseBlend() {
        description = "HouseBlend Coffee";
    }

    @Override
    public double cost() {
        return 0.89;
    }
}
// 调料装饰类
abstract class CondimentDecorator extends Beverage{
    @Override
    public abstract String getDescription();
}

class Mocha extends CondimentDecorator {
    Beverage beverage;

    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost() {
        return .21 + beverage.cost();
    }
}
class Soy extends CondimentDecorator{
    Beverage beverage;

    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return .1 + beverage.cost();
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }
}
public class Description{
    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        Beverage beverage1 = new HouseBlend();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Soy(beverage1);
        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());
    }
}