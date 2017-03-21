package com.zjluoyue.io;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Jia on 2017/3/20.
 */
public class FormattedMemoryInput {
    public static void main(String[] args) {
        try {
            DataInputStream in = new DataInputStream(
                    new ByteArrayInputStream(
                            BufferedInputFile.read(
                                    "src/main/java/com/zjluoyue/io/FormattedMemoryInput.java")
                                    .getBytes()));
            while (true)
                System.out.println((char) in.readByte());
        } catch (IOException e) {
            System.err.println("End of Stream");
        }
    }
}
