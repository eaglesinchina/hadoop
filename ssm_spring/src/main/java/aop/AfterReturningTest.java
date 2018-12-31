package aop;

import org.springframework.aop.AfterReturningAdvice;
import java.lang.reflect.Method;

public class AfterReturningTest implements AfterReturningAdvice {
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("after....");
    }
}
