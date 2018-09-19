import a.HelloI;
import a.b.HelloI2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test2 {
    @org.junit.Test
    public void t(){

        //创建： 上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
       HelloI2 h= (HelloI2) context.getBean("hello2");

       h.sayBye();
    }

}
