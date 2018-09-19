package java_proxy_dynamic;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Proxy_school {
    //持有：对象引用
    Target_educator edu;

    //构造
    public Proxy_school(Target_educator edu) {
        this.edu = edu;
    }

    public Target_educator newEdutor() throws Throwable {

        return (Target_educator)Proxy.newProxyInstance(
                edu.getClass().getClassLoader(),
                edu.getClass().getInterfaces(),
                new InvokHandler()
        );
    }

    //内部类
    private  class InvokHandler implements InvocationHandler {

        //
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("代理( " +edu.getClass().getName()+" )---执行  "+method.getName() + " 方法");
            System.out.println("开学典礼....分班..提供食宿...");
            method.invoke(edu,args);
            System.out.println("成绩考核...发证书..毕业典礼..");

            return null;
        }
    }

}
