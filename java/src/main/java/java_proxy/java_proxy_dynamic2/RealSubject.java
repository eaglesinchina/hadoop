package java_proxy.java_proxy_dynamic2;

public class RealSubject implements Subject {

    public String doSomething(String name) {
        System.out.println(name + " do something!");
        return name + " do something!";
    }
}

