import a.HelloImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test3 {
    ApplicationContext context;

    @Before
    public void init() {
        //创建： 上下文
        context = new ClassPathXmlApplicationContext("beans.xml");
    }

    /**
     * 注解： 创建对象--@service, @resource ,@scope(value="")
     */
    @Test
    public void t2() {
        a.b2.HelloI2 h = (a.b2.HelloI2) context.getBean("hello22");
        h.sayBye();

        a.b2.HelloI2 h2 = (a.b2.HelloI2) context.getBean("hello22");
        System.out.println(h + "," + h2);
    }

    /**
     * 构造方法： 创建对象 <constructor-arg type="int" value="1" >
     */
    @Test
    public void t3() {
        HelloImpl h = (HelloImpl) context.getBean("helloImpl2");
        h.say();
    }

    /**
     * 构造方法： 创建对象  <constructor-arg type="java.lang.String" value="aa" index="0">
     */
    @Test
    public void t32() {
        HelloImpl h = (HelloImpl) context.getBean("helloImpl3");
        h.say();
    }
}
