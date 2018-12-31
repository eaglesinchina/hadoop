package nio.ref.ref2;

import org.junit.Test;
import qq.base.util.NumberUtils;
import nio.ref.Student;

import java.beans.*;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class RefPer2 {
    @Test
    public void t1() throws Exception {
        /**
         * get--->set方法名,  参数
         */

        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");
        Student st2 = new Student();


        //get方法---》set赋值
        Class clazz = st.getClass();
        Method[] ms = clazz.getMethods();

        //判断： 是否getAge()
        for (Method m : ms) {

            String get = m.getName();
            Class<?>[] paramType = m.getParameterTypes();
            Class<?> retType = m.getReturnType();

            if (get.startsWith("get") && paramType.length == 0 && retType != Class.class) {
                Object returnVal = m.invoke(st);
                String set = get.replace("get", "set");

                //set : 参数---->第二个Obj赋值
                Method setMetho = clazz.getMethod(set, retType);
                setMetho.invoke(st2, returnVal);
            }
        }

        System.out.println(st2.getName() + "," + st2.getAge() );
    }


    @Test
    public void t2() throws Exception {

        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");
        st.setScore(99);
        Student st2 = new Student();

        /**
         * 内省： 实现---2个对象：  属性拷贝
         */
        BeanInfo binfo = Introspector.getBeanInfo(st.getClass());
        PropertyDescriptor[] propDes = binfo.getPropertyDescriptors();

        for (PropertyDescriptor proDescriptor : propDes) {

            Method read = proDescriptor.getReadMethod();
            Method write = proDescriptor.getWriteMethod();
            String fieldName = proDescriptor.getName();

            if (read != null && write != null) {
                if (!fieldName.equals("class"))
                    write.invoke(st2, read.invoke(st));
            }
        }
        System.out.println(st2.getName() + "," + st2.getAge()+ ","+st2.getScore());
    }


    @Test
    public void t3() throws Exception {
        /**
         * 不同对象： 属性的拷贝
         */

        //找出： setName()  --setName()

        //dog: name---student:name
        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");
        st.setScore(99);

        Class<?> clazz = Class.forName("ref.Student");
        Constructor<?> cons = clazz.getDeclaredConstructor();
        Student student = (Student) cons.newInstance();

        Dog dog = new Dog();

        //赋值
        BeanInfo binfo = Introspector.getBeanInfo(st.getClass());
        PropertyDescriptor[] propDes = binfo.getPropertyDescriptors();

        BeanInfo binfo2 = Introspector.getBeanInfo(dog.getClass());
        PropertyDescriptor[] propDes2 = binfo2.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propDes) {
            Method read = propertyDescriptor.getReadMethod();
            Method write = propertyDescriptor.getWriteMethod();

            System.out.println(read + ",  " + write);
            if (read != null && write != null) {

                for (PropertyDescriptor propertyDescriptor2 : propDes2) {
                    Method read2 = propertyDescriptor2.getReadMethod();
                    Method write2 = propertyDescriptor2.getWriteMethod();

//                    write2.invoke(dog, read2.invoke(st));
                    if (read2.getName().equalsIgnoreCase(read.getName()) && write2.getName().equalsIgnoreCase(write2.getName())) {
                        System.out.println(read.getName());
                        write2.invoke(dog, read.invoke(st));
                    }

                }
            }
        }

        System.out.println(dog.getName());
    }

    /**
     * 内省机制： 2个对象---属性拷贝
     * @throws Exception
     */
    @Test
    public void t4() throws Exception {
        //dog: name---student:name
        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");

        Dog dog = new Dog();

        //存储数据
        HashMap<String, Method> map = new HashMap<>();

        //判断： name
        BeanInfo beanInfo = Introspector.getBeanInfo(st.getClass());
        PropertyDescriptor[] propDesc = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propDesc) {

            String filedName = propertyDescriptor.getName();
//            System.out.println(filedName);
            map.put(filedName, propertyDescriptor.getReadMethod());

        }


        BeanInfo beanInfo2 = Introspector.getBeanInfo(dog.getClass());
        PropertyDescriptor[] propDesc2 = beanInfo2.getPropertyDescriptors();

        for (PropertyDescriptor propertyDescriptor : propDesc2) {

            Method read = map.get(propertyDescriptor.getName());
            Method write = propertyDescriptor.getWriteMethod();

            if (read != null && write != null) {
                write.invoke(dog, read.invoke(st));
            }
        }

        //map   { getName():  值}， -----{setName()， 值}
        System.out.println(dog.getName());
    }

    @Test
    public void t5() throws Exception {
        //dog: name---student:name
        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");

        Dog dog = new Dog();


        //判断： name
        BeanInfo beanInfo = Introspector.getBeanInfo(st.getClass());
        PropertyDescriptor[] propDesc = beanInfo.getPropertyDescriptors();

        BeanInfo beanInfo2 = Introspector.getBeanInfo(dog.getClass());
        PropertyDescriptor[] propDesc2 = beanInfo2.getPropertyDescriptors();


        for (PropertyDescriptor propertyDescriptor : propDesc) {
            String filedName = propertyDescriptor.getName();

            for (PropertyDescriptor propertyDescriptor2 : propDesc2) {
                String filedName2 = propertyDescriptor.getName();

                //属性：相同
                Method write = propertyDescriptor2.getWriteMethod();
                Method read = propertyDescriptor.getReadMethod();

                if (filedName.equals(filedName2) && read != null && write != null) {

                    try {
                        write.invoke(dog, read.invoke(st));
                    } catch (Exception e) {

                    }

                }


            }

        }

        //map   { getName():  值}， -----{setName()， 值}
        System.out.println(dog.getName());
    }


    @Test
    public void t6() throws IntrospectionException {
        Class<A> clazz = A.class;
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        PropertyDescriptor[] propDes = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propDe : propDes) {

            String filedName = propDe.getName();
//            Method read = propDe.getReadMethod();
//            Method write = propDe.getWriteMethod();


            System.out.println(filedName);//+"," +read.getName() );//+","+write.getName());
        }


    }

    @Test
    public void t7() throws IOException, ClassNotFoundException {
        Student st = new Student();
        st.setAge(40);
        st.setName("aaaa");

        //把各个属性：  组合为byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream objout = new ObjectOutputStream(baos);
        objout.writeObject(st);
        baos.close();
        objout.close();

        ObjectInputStream objin = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        Student student= (Student) objin.readObject();
        objin.close();
        System.out.println(student.getName() + "," + student.getAge()+","+student.getScore());

    }


}

class A {

//
//    public int getAge() {
//        return 1;
//    }

    public void setAge(int age) {

    }
}