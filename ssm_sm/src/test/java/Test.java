import a.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.UserServiceImpl;

public class Test {

    @org.junit.Test
    public void t(){

        //加载配置
        ApplicationContext context= new ClassPathXmlApplicationContext("beans.xml");
        UserServiceImpl service= (UserServiceImpl) context.getBean("userServiceImpl");

        User user = new User(23,"test-ssm","boy");
        int res = service.insert(user);
        System.out.println(res);

    }
}
