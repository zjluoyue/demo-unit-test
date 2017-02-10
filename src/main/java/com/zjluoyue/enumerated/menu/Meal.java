package com.zjluoyue.enumerated.menu;

/**
 * Created by Jia on 2017/2/9.
 */
public class Meal {
    public static void main(String[] args) {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            System.out.println(food);
        }
    }
}
