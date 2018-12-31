import a.HelloI;
import aop2.Actor;
import aop3.Singer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_aop2 {
    @Test
    public void t(){

        //创建： 上下文
        ApplicationContext context = new ClassPathXmlApplicationContext("aop2.xml");
        Actor  actor= (Actor) context.getBean("singer");

       actor.show();
    }

}
