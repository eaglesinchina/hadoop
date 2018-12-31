package nio.ref;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RefPer {

    @Test
    public void t() throws Exception {
        //对象属性：  拷贝
        //1，创建对象： 有属性
        Per p = new Per(12, "lisi");
        //2, 创建对象： 无属性
        Class<?> clazz = Class.forName("ref.Per");

        //获取字段
        Field age = clazz.getDeclaredField("age");
        age.setAccessible(true);
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);

        //赋值
        Per person = (Per) clazz.newInstance();
        age.set(person, p.getAge());
        name.set(person, p.getName());
        System.out.println(person.getName() + "," + person.getAge());
    }

    @Test
    public void t2() throws Exception {
        //1，创建对象： 有属性
        Per p = new Per(12, "lisi");

        //2, 创建对象： 无属性
        Class<?> clazz = Class.forName("ref.Per");

        Method setAge = clazz.getDeclaredMethod("setAge", int.class);
        setAge.setAccessible(true);
        Method setName = clazz.getDeclaredMethod("setName", String.class);
        setName.setAccessible(true);

        //赋值
        Per person = (Per) clazz.newInstance();
        setAge.invoke(person, p.getAge());
        setName.invoke(person, p.getName());
        System.out.println(person.getName() + "," + person.getAge());

    }

    @Test
    public void t3() throws Exception {

        //创建对象： 带参构造
        Class<?> clazz = Class.forName("ref.Per");
        //构造
        Constructor<?> cons = clazz.getDeclaredConstructor(int.class, String.class);
        Per person = (Per) cons.newInstance(23, "aaa");
        System.out.println(person.getName() + "," + person.getAge());
    }

    /**
     * 反射： 通过属性---copy对象
     * @throws Exception
     */
    @Test
    public void t4() throws Exception {
        //创建对象： 有属性
        Student st = new Student();
        st.setName("yyyy");
        st.setAge(23);

        //创建对象： 带参构造
        Class<?> clazz = Class.forName("ref.Student");
        Constructor<?> cons = clazz.getDeclaredConstructor(int.class);
        Student student = (Student) cons.newInstance(80);

        //获取: 父类属性--------------------------
        Class<?> sup = clazz.getSuperclass();
        Field age = sup.getDeclaredField("age");
        age.setAccessible(true);
        Field name = sup.getDeclaredField("name");
        name.setAccessible(true);

        //赋值
        age.set(student,st.getAge());
        name.set(student,st.getName());
        System.out.println(student.getName() + "," + student.getAge()+","+student.getScore());
    }

    /**
     * 反射： 通过方法----copy对象
     * @throws Exception
     */
    @Test
    public void t42() throws Exception {
        //创建对象： 有属性
        Student st = new Student();
        st.setName("yyyy");
        st.setAge(23);

        //创建对象： 带参构造
        Class<?> clazz = Class.forName("ref.Student");
        Constructor<?> cons = clazz.getDeclaredConstructor(int.class);
        Student student = (Student) cons.newInstance(89);

        //获取: 父类方法
        Method setAge = clazz.getMethod("setAge", int.class);
        Method setName = clazz.getMethod("setName", String.class);

        //赋值
        setAge.invoke(student, st.getAge());
        setName.invoke(student, st.getName());
        System.out.println(student.getName() + "," + student.getAge()+","+student.getScore());
    }

    @Test
    public void t5(){
        /**
         * 测试：  子类---是否 有  父类 ======方法， 属性
         */

        Student st = new Student();
        Class<? extends Student> clazz = st.getClass();

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }

        System.out.println("==========");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    @Test
    public void t6() throws Exception {

        /**
         * 测试： 类.newInstance();   类.getdeclaredConstructor.newInstance()
         */

        Class<?> clazz = Class.forName("ref.Student");
        clazz.newInstance();

        clazz.getDeclaredConstructor().newInstance();
    }
}
