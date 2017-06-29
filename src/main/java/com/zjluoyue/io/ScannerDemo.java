package com.zjluoyue.io;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Jia on 17/3/21.
 */
public class ScannerDemo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        int N = in.nextInt();
        int d = in.nextInt();
        in.nextLine();
        String[][] s = new String[N][];
        int i = 0;
        while (i < N) {
            String reader = in.nextLine();
            s[i] = reader.split(" ");
            i++;
        }
        in.close();
        System.out.println(N + " " + d);
        System.out.println(Arrays.deepToString(s));

    }
}
