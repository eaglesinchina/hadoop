import aop3.Actor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_aop_around {

    @Test
    public void t2(){

        ApplicationContext context = new ClassPathXmlApplicationContext("aop3.xml");
        Actor singer= (Actor) context.getBean("singer");
        singer.show();
    }

}
