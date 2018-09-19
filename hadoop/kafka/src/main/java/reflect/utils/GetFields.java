package reflect.utils;

import reflect.logs.AppErrorLog;

import java.lang.reflect.Field;

public class GetFields {
    
    //使用反射： 获取子类属性，  父类属性

    public static void main(String[] args) {
        //本类
        Class<AppErrorLog> clas = AppErrorLog.class;
        Field[] fs = clas.getDeclaredFields();

        System.out.println("本类： fields....");
        for (Field f : fs) {
            System.out.println(f.getName());

        }

        //父类
        System.out.println("父类： fields....");
        Class<? super AppErrorLog> superclass = clas.getSuperclass();
        Field[] fs2 = superclass.getDeclaredFields();
        for (Field f : fs2) {
            System.out.println(f.getName());

        }

    }
}

class A{
    int age;
    private String job;
}

class  B extends  A{
    private String name;
}