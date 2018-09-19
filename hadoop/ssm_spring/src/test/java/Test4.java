import a.HelloI;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test4 {
    @Test
    public void t(){

        //创建： 上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
        HelloI h= (HelloI) context.getBean("proxyBean");

        h.say();
    }

}
