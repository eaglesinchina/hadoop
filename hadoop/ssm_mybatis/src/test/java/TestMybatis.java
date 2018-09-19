import a.Orders;
import a.User;
import b.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TestMybatis {
    SqlSession session;

    @Before
    public void init() throws IOException {
        //加载文件
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");

        //创建session
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
         session = factory.openSession();
    }

    /**
     * 插入
     * @throws IOException
     */
    @Test
    public void t() throws IOException {
        //crud
        int res = session.insert("users.insert", new User(23, "insert3", "boy"));

        session.commit();
        session.close();
        System.out.println("ok,  " +res);
    }

    /**
     * 更新
     * @throws IOException
     */
    @Test
    public void t2() throws IOException {
        //crud
        int res = session.update("users.update", new User(1, 23, "updte2", "girl"));

        session.commit();
        session.close();
        System.out.println("ok,  "+ res);
    }

    /**
     * 查询
     * @throws IOException
     */
    @Test
    public void t3() throws IOException {
        //crud
        User user = session.selectOne("users.selectById", 1);

        session.commit();
        session.close();
        System.out.println("ok");
        System.out.println(user);
    }
    /**
     * 删除
     * @throws IOException
     */
    @Test
    public void t4() throws IOException {
        //crud
        int res = session.delete("users.delete");

        session.commit();
        session.close();
        System.out.println("ok, "+res);

    }
    /**
     * 查询 所有
     * @throws IOException
     */
    @Test
    public void t40() throws IOException {
        //crud
        User user = session.selectOne("users.selectById", 1);

        session.commit();
        session.close();
        System.out.println("ok");
        System.out.println(user);
    }

    /**
     * 查询 所有（ order-->user)
     * @throws IOException
     */
    @Test
    public void t42() throws IOException {
        //crud
        Orders orders=session.selectOne("orders.selectById",2);

        session.commit();
        session.close();
        System.out.println("ok");

        System.out.println(orders);
    }

    /**
     * 查询 所有（user ---> order)
     * @throws IOException
     */
    @Test
    public void t43() throws IOException {
        //crud
        User user=session.selectOne("users.selectOrderListById",1);

        session.commit();
        session.close();
        System.out.println("ok");

        System.out.println(user);
    }

    /**
     * 查询 所有（user ---> order-->product)
     * @throws IOException
     */
    @Test
    public void t44() throws IOException {
        //crud
        b.User user=session.selectOne("users.selectOrderListDetailsById",1);

        session.commit();
        session.close();
        System.out.println("ok");

        System.out.println(user);
    }

    /**
     * 查询 所有（product ---> order-->users)
     * @throws IOException
     */
    @Test
    public void t45() throws IOException {
        //crud
       Product p=session.selectOne("product.selectById",1);

        session.commit();
        session.close();
        System.out.println("ok");

        System.out.println(p);
    }












    /**
     * 删除
     * @throws IOException
     */
    @Test
    public void t5() throws IOException {
        //crud
        int res = session.delete("users.delete",2);

        session.commit();
        session.close();
        System.out.println("ok");
        System.out.println(res);
    }


}
