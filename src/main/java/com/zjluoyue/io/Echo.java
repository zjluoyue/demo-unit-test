package com.zjluoyue.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Jia on 2017/3/20.
 */
public class Echo {
    public static void main(String[] args) throws IOException {
        BufferedReader stdin =
                new BufferedReader(new InputStreamReader(System.in));
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s=stdin.readLine()) != null && s.length() != 0)
            sb.append(s + "\n");
        String[] strings = sb.toString().split("\n");
        System.out.println(Arrays.toString(strings));
    }
}
