package nio.ref;

import java.io.Serializable;

public class Per implements Serializable {
    //属性
    private int age;
    private String name;

    //构造
    public Per(int age, String name) {
        this.age = age;
        this.name = name;
    }
    public Per() {
        System.out.println("per 无参构造");
    }

    //get,set
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
