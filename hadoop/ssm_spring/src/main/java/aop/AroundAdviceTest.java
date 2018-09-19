package aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AroundAdviceTest implements MethodInterceptor {

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start=System.nanoTime();
        System.out.println("--------方法  begin");

        Object res = methodInvocation.proceed();
        System.out.println("--------方法  end ---time="+ (System.nanoTime()-start));
        return res;
    }
}
