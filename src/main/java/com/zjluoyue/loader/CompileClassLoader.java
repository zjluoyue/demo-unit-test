package com.zjluoyue.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by Jia on 17/3/22.
 */
public class CompileClassLoader extends ClassLoader {
    //读取一个文件的内容
    private byte[] getBytes(String filename) throws IOException {
        File file = new File(filename);
        long len = file.length();
        byte[] raw = new byte[(int)len];
        try(FileInputStream fin = new FileInputStream(file)){
            //一次读取Class文件的全部二进制数据
            int r = fin.read(raw);
            if (r != len){
                throw new IOException("无法读取全部文件" + r + " != " + len);
            }
            return raw;
        }
    }
    // 定义编译指定Java文件的方法
    private boolean compile(String javaFile) throws IOException{
        System.out.println("CompileClassLoader：正在编译" + javaFile + "...");
        // 调用系统的javac命令
        Process p = Runtime.getRuntime().exec("javac " + javaFile);
        try{
            //当前线程等待p所表示的进程执行完成
            p.waitFor();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        // 获取javac线程的退出值
        int ret = p.exitValue();
        // 返回编译是否成功
        return ret == 0;
    }
    // 重写ClassLoader的findClass方法
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        Class clazz = null;
        // 将包路径中的点替换成斜线
        String fileStub = name.replace(".", "/");
        String javaFilename = fileStub + ".java";
        String classFilename = fileStub + ".class";
        File javaFile = new File(javaFilename);
        File classFile = new File(classFilename);
        //当指定Java源文件存在，且Class文件不存在，
        //或者Java源文件的修改时间比Class文件的修改时间更晚时，重新编译
        if (javaFile.exists() && (!classFile.exists()
                || javaFile.lastModified() > classFile.lastModified())){
            try{
                //如果编译失败，或者该Class文件不存在
                if (!compile(javaFilename) || !classFile.exists()){ //这一步compile重新编译了一次
                    throw new ClassNotFoundException("ClassNotFoundException:" + javaFilename);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        // 如果Class文件存在，系统负责将该文件转换成Class对象
        if (classFile.exists()){
            try{
                //将Class文件的二进制数据读入数组
                byte[] raw = getBytes(classFilename);
                // 调用ClassLoader的defineClass方法将二进制数据成Class对象
                clazz = defineClass(name, raw, 0, raw.length);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        //如果clazz为null，则表明加载失败，抛出异常
        if (clazz == null){
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }
    public static void main(String[] args) throws Exception {
        //如果运行该程序时没有参数，即没有目标类
        if (args.length < 1){
            System.out.println("缺少目标类，请按如下格式运行源文件：");
            System.out.println("java CompileClassLoader ClassName");
        }
        // 第一个参数是需要运行的类
        String progClass = args[0];
        // 剩下的参数作为运行目标类时的参数
        // 将这些参数复制到一个新数组中
        String[] progArgs = new String[args.length-1];
        System.arraycopy(args, 1, progArgs, 0, progArgs.length);
        CompileClassLoader ccl = new CompileClassLoader();
        // 加载需要运行的类
        Class<?> clazz = ccl.loadClass(progClass);
        // 获取需要运行的类的主方法
        Method main = clazz.getMethod("main", (new String[0]).getClass());
        Object argsArray[] = {progArgs};
        main.invoke(null, argsArray);
    }
}
