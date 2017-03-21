package com.zjluoyue.io;

import java.io.*;

/**
 * Created by Jia on 2017/3/20.
 */
public class Redirecting {
    public static void main(String[] args) throws IOException {
        PrintStream console = System.out;
        BufferedInputStream in =
                new BufferedInputStream(new FileInputStream
                        ("src/main/java/com/zjluoyue/io/Redirecting" +
                        ".java"));
        PrintStream out = new PrintStream(
                new BufferedOutputStream(new FileOutputStream("test.out")));
        System.setIn(in);
        System.setOut(out);
        System.setErr(out);
        BufferedReader br =
                new BufferedReader(new InputStreamReader(System.in));
        String s;
        while ((s=br.readLine()) != null)
            System.out.println(s);
        System.out.println("end");
        out.close();
        System.setOut(console);
    }
}
