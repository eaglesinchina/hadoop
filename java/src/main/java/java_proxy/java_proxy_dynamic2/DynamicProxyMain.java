package java_proxy.java_proxy_dynamic2;

import java.lang.reflect.Proxy;

public class DynamicProxyMain {

     public static void main(String[] args){
         Subject realSubject = new RealSubject();

         InvocationHandler handler = new DynamicProxyAOP(realSubject);

         Subject subject = (Subject) Proxy.newProxyInstance(
                 realSubject.getClass().getClassLoader(),
                 realSubject.getClass().getInterfaces(),
                 (java.lang.reflect.InvocationHandler) handler);

         String rtn = subject.doSomething("tester ");
     }
 }
