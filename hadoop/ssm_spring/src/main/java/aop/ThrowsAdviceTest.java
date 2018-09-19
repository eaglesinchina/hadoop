package aop;

import org.springframework.aop.ThrowsAdvice;
import java.lang.reflect.Method;

public class ThrowsAdviceTest implements ThrowsAdvice {
    public void afterThrowing(Method method, Object[] args, Object target, Exception ex){
        System.out.println("error....");
        ex.printStackTrace();
    }
}
