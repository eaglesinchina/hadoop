import d.Husband;
import d.Wife;
import d.d2.Student;
import d.d2.Teacher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Test_st {
    SqlSession session;
    @Before
    public void init() throws IOException {
        //加载配置文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        //创建session工厂, session
        session = new SqlSessionFactoryBuilder().build(in).openSession();
    }

    @Test
    public void t1(){
       //创建对象
        Teacher t1 = new Teacher("t1");
        Teacher t2 = new Teacher("t2");

        Student s1 = new Student("s1");
        Student s2 = new Student("s2");
        Student s3 = new Student("s3");
        Student s4 = new Student("s4");

        //关联关系
        t1.add(s1,s2,s3);
        t2.add(s2,s3,s4);

        //插入数据

        int res2 = session.insert("student.insert", s1);
         session.insert("student.insert", s2);
         session.insert("student.insert", s3);
         session.insert("student.insert", s4);
        int res1 = session.insert("teacher.insert", t1);
        session.insert("teacher.insert", t2);


        session.insert("teacher.insertLinks", t1);
        session.insert("teacher.insertLinks", t2);

        session.commit();
        session.close();
        System.out.println(res1+","+res2);
    }

}
