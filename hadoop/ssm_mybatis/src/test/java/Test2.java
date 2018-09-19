import c.Area;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class Test2 {
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
        Area china = new Area("china");
        Area hebei = new Area("hebei");
        Area henan = new Area("shanxi");

        Area baoding = new Area("baoding");
        Area langfang = new Area("langfang");
        Area luoyang = new Area("luoyang");

        china.add(hebei,henan);
        hebei.add(baoding,langfang);
        henan.add(luoyang);

        int res = session.insert("area.insert", china);
        int res2 = session.insert("area.insert", hebei);
        int res3 = session.insert("area.insert", henan);
        int res5 = session.insert("area.insert", baoding);
        int res6 = session.insert("area.insert", langfang);
        int res7 = session.insert("area.insert", luoyang);

        session.commit();
        session.close();
        System.out.println("res = "+res);
        System.out.println("res2 = "+res2);
        System.out.println("res3 = "+res3);
    }
}
