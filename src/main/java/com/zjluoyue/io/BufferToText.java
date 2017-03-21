package com.zjluoyue.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by Jia on 2017/3/21.
 */
public class BufferToText {
    private static final int BSIZE = 1024;
    // 字节到字符的转换
    public static void main(String[] args) throws IOException {

        FileChannel fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes()));
        fc.close();
        fc = new FileInputStream("data2.txt").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        buffer.flip();
        // 不能运行
        System.out.println(buffer.asCharBuffer());
        // 输出时进行解码
        buffer.rewind();
        String encoding = System.getProperty("file.encoding");
        System.out.print("Decoded using " + encoding + ": ");
        System.out.println(Charset.forName(encoding).decode
                (buffer));
        // 输入时编码
        fc = new FileOutputStream("data2.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text".getBytes("UTF-16BE")));
        fc.close();
        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());

        fc = new FileOutputStream("data2.txt").getChannel();
        buffer = ByteBuffer.allocate(18);
        buffer.asCharBuffer().put("Some text");
        fc.write(buffer);
        fc.close();
        fc = new FileInputStream("data2.txt").getChannel();
        buffer.clear();
        fc.read(buffer);
        buffer.flip();
        System.out.println(buffer.asCharBuffer());
    }
}
