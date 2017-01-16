package com.zjluoyue.myregex;

import java.util.regex.Pattern;

/**
 * Created by zjluoyue on 2016/9/27.
 */
public class StrRegex {
    public static void main(String[] args) {
        String regEx="\\w+\\d+";
        String[] str = {"userLadder", "userMoney4", "userMoney41"};

        for (String s: str) {
            System.out.println(Pattern.compile(regEx).matcher(s).matches());
        }
    }
}
