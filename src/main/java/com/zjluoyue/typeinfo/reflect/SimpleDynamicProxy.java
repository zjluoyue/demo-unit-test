package com.zjluoyue.typeinfo.reflect;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by zjluoyue on 2016/10/15.
 */
public class SimpleDynamicProxy {

    interface Interface {
        void doSomething();

        void somethingElse(String arg);
    }


    class RealObject implements Interface {

        public void doSomething() {
            System.out.println("doSomething!");
        }

        public void somethingElse(String arg) {
            System.out.println("somethingElse " + arg);
        }
    }

    class SimpleProxy implements Interface {
        private Interface proxied;

        public SimpleProxy(Interface proxied) {
            this.proxied = proxied;
        }
        public void doSomething() {
            System.out.println("SimpleProxy doSomething");
        }

        public void somethingElse(String arg) {
            System.out.println("SimpleProxy somethingElse " + arg);
            proxied.somethingElse(arg);
        }
    }

    public static void consumer(Interface iface) {
        iface.doSomething();
        iface.somethingElse("bonobo");
    }

    public static void main(String[] args) {
        SimpleDynamicProxy sdp = new SimpleDynamicProxy();
        // 代理
//        consumer(sdp.new RealObject());
//        consumer(sdp.new SimpleProxy(sdp.new RealObject()));
        RealObject real = sdp.new RealObject();
        consumer(real);
        // 应用动态方式进行代理
        Interface proxy = (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                new Class[]{Interface.class},
                sdp.new DynamicProxyHandler(real));
        consumer(proxy);
    }

    class DynamicProxyHandler implements InvocationHandler{
        private Object proxied;

        public DynamicProxyHandler(Object proxied) {
            this.proxied = proxied;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("**** proxy: " + proxy.getClass() +
            ", method: " + method + ", args: " + args);
            if (args != null) {
                for (Object arg : args) {
                    System.out.println("  " + arg);
                }
            }
            return method.invoke(proxied, args);
        }
    }

}
