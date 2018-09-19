package aop3;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

public class App {
    @Autowired
    Audience a ;

    public Object watch(ProceedingJoinPoint pjp) {

        //调用： 演员方法---环绕增强【观众】
        try {
            a.sitDown();
            Object res = pjp.proceed();
            a.applaud();


            return res;
        } catch (Throwable e) {

            e.printStackTrace();
            a.payOff();
        }finally {
            a.goHome();
        }


        return null;
    }
}
