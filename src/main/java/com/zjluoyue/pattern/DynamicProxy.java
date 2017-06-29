package com.zjluoyue.pattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jia on 2017/6/28.
 */
public class DynamicProxy {

    public static void main(String[] args) {
        UserServerProxy userServerProxy = new UserServerProxy();

        UserServer userServer = userServerProxy.getUserServerProxy();

        System.out.println(userServer.getName(1));
        System.out.println(userServer.getId(1));
    }
}

/**
 * 接口
 */
interface UserServer{

    String getName(int id);

    Integer getId(int id);
}

/**
 * 实现
 */
class UserServerImpl implements UserServer {
    @Override
    public String getName(int id) {
        System.out.println("------getName------");
        return "Tom";
    }

    @Override
    public Integer getId(int id) {
        System.out.println("------getAge------");
        return 10;
    }
}

/**
 * 代理持有类
 */
//class MyInvocationHandler implements InvocationHandler {
//    private Object target;
//
//    MyInvocationHandler() {
//        super();
//    }
//
//    MyInvocationHandler(Object target) {
//        super();
//        this.target = target;
//    }
//
//    @Override
//    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
//        if("getName".equals(method.getName())){
//            System.out.println("++++++before " + method.getName() + "++++++");
//            Object result = method.invoke(target, args);
//            System.out.println("++++++after " + method.getName() + "++++++");
//            return result;
//        }else{
//            Object result = method.invoke(target, args);
//            return result;
//        }
//
//    }
//}

/**
 * 代理
 */
class UserServerProxy {
    /**
     * 目标对象
     */
    private UserServer userServer = new UserServerImpl();

    /**
     * 生成一个代理对象
     * @return
     */
    public UserServer getUserServerProxy() {
        return (UserServer) Proxy.newProxyInstance(UserServerProxy.class.getClassLoader(),
                userServer.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        if ("getName".equals(method.getName())) {
                            System.out.println("++++++before " + method.getName() + "++++++");
                            Object result = method.invoke(userServer, args);
                            System.out.println("++++++after " + method.getName() + "++++++");
                            return result;
                        } else {
                            Object result = method.invoke(userServer, args);
                            return result;
                        }
                    }
                });
    }
}