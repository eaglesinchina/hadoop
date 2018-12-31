package a.b2;

import a.HelloImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("hello22")
//@Scope(value = "prototype")
@Scope(value = "singleton")
public class HelloImpl2 implements HelloI2 {

    String name;

    @Resource(name="helloImpl")
    HelloImpl hImp;

    //构造
    public HelloImpl2(String name) {
        this.name = name;
    }
    public HelloImpl2() { }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sayBye() {
        System.out.println("bye  "+ name);
        hImp.say();
    }
}
