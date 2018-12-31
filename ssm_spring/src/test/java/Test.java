import a.HelloI;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.Test
    public void t(){

        //创建： 上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       HelloI h= (HelloI) context.getBean("helloImpl");

       h.say();
    }

    @org.junit.Test
    public void t2(){

        //创建： 上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        HelloI h= (HelloI) context.getBean("helloImpl");
        h.say();
    }
}
