package ref.proxy;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import ref.Per;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProTest {
    /**
     * 代理模式
     */

    public InvocationHandler createHandler() {

        //对象： 接口实现类
        final P1 p1 = new P1();

        // 处理器
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object res = null;

                //方法增强： 前后控制
                if (method.getName().equals("sleep")) {
                    System.out.println();
                    System.out.println("----------handler  before--- 增强sleep ..");
                    res = method.invoke(p1, args);
                    System.out.println("----------handler  after---增强sleep ..\n");

                } else if (method.getName().equals("say")) {
                    System.out.println("=========");

                    Person p = (Person) proxy;
                    p.sleep();

                    System.out.println("*********handler  before---增强say ..");
                    res = method.invoke(p1, args);
                    System.out.println("*********handler  after---增强say ..");

                    System.out.println("=========");


                } else {//不增强方法

                    res = method.invoke(p1, args);
                }
                return res;
            }
        };

        //返回： 接口实现类
        return handler;
    }

    @Test
    public void newInstance() {
        // 代理工具类Proxy ：创建对象 ( 接口，  处理器）
        Object obj = Proxy.newProxyInstance(

                ProTest.class.getClassLoader(),
                new Class[]{Person.class, Work.class},
                createHandler()
        );
        Person person = (Person) obj;
        person.say("lilsi");

        // 代理类： 创建对象
        Work worker = (Work) obj;
        worker.work();
    }
}
//=================================

interface Person {
    void say(String name);

    void sleep();
}

interface Work {
    void work();
}

class P1 implements Person, Work {

    @Override
    public void say(String name) {
        System.out.println("hello " + name);
    }

    @Override
    public void sleep() {
        System.out.println("p1  sleep....");
    }

    @Override
    public void work() {
        System.out.println("work.....");
    }
}
