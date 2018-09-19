package aop;

import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

public class BeforeMethodTest implements MethodBeforeAdvice {
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("before....");
    }
}
