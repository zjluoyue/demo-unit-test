package com.zjluoyue.loader;

import java.lang.reflect.Method;

public class ClassLoaderTree {

    public static void main(String[] args) {
        ClassLoader loader = ClassLoaderTree.class.getClassLoader();
        while (loader != null) {
            System.out.println(loader.toString());
            loader = loader.getParent();
        }
        System.out.println("Sample loader......");
        String classDataRootPath = ".";
        FileSystemClassLoader fscl1 = new FileSystemClassLoader(classDataRootPath);
        NetworkClassLoader fscl2 = new NetworkClassLoader(classDataRootPath);
        String className = "com.zjluoyue.loader.Sample";
        CompileClassLoader ccl = new CompileClassLoader();
        try {
            Class<?> class1 = fscl1.loadClass(className);
            Object obj1 = class1.newInstance();
            Class<?> class2 = fscl2.loadClass(className);
            Object obj2 = class2.newInstance();
            Class<?> class3 = Class.forName(className);
            Object obj3 = class3.newInstance();
            Sample obj5 = new Sample();
            Class<?> class4 = ccl.loadClass(className);
            Object obj4 = class4.newInstance();
            Method setSampleMethod = class1.getMethod("setSample", java.lang.Object.class);
            setSampleMethod.invoke(obj1, obj2);
            setSampleMethod.invoke(obj1, obj3);
            setSampleMethod.invoke(obj4, obj1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Sample loaded.");
    }
}
