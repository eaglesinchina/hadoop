package java_proxy.java_proxy_dynamic2;

import java.lang.reflect.Method;

public class DynamicProxyAOP implements InvocationHandler {
    private Object subject;

    public DynamicProxyAOP(Object subject) {
        this.subject = subject;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
   //在执行真实subject执行的方法
        System.out.println("before do something");
    // 执行真实subject方法
        Object rtn = method.invoke(subject, args);
    // 在执行结束后再执行的方法
        System.out.println("after do something");

     return rtn;
    }
}


