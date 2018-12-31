package a;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class HelloImpl implements HelloI {
   private String name;
    //构造
    public HelloImpl( String b, int a){ System.out.println("String b ,int a"); }
    public HelloImpl(int a, String b){
        System.out.println("int a, String b ");
    }
    public HelloImpl(Integer a, String b){ System.out.println("Integer a, String b ");}
    public HelloImpl(String name) { this.name = name; }
    public HelloImpl() { }

    public void say() { System.out.println("hello  "+ name); }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
