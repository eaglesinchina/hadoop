package a.b;

import org.springframework.stereotype.Service;

@Service("hello2")
public class HelloImpl2 implements HelloI2 {

    String name;
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
    }
}
