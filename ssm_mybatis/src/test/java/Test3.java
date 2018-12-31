import c.Area;
import d.Husband;
import d.Wife;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Test3 {
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
        Husband husband = new Husband("a");
        Wife wife = new Wife("lili", husband);

        //
        int res2 = session.insert("husband.insert", husband);
        int res1 = session.insert("wife.insert", wife);

        session.commit();
        session.close();
        System.out.println(res1+","+res2);
    }
    @Test
    public void t12(){
        //
        Husband h=session.selectOne("husband.select",1);

        session.commit();
        session.close();

        System.out.println(h);
    }
}
