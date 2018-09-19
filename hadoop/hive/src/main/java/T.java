import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class T {

    @Test
    public void t1() throws  Exception{
       //  Class.forName("org.apache.hive.jdbc.HiveDriver");
         //建立连接
        Connection con = DriverManager.getConnection("jdbc:hive2://s101:10000/big12");

        //查询
        Statement stm = con.createStatement();
        ResultSet res = stm.executeQuery("select * from stu");

        while (res.next()){
            System.out.println(res.getString(1)+", "+res.getString(2));
        }

        //关闭资源
        res.close();
        con.close();
    }

    //插入数据
    @Test
    public void t2() throws  Exception{

        Connection con = DriverManager.getConnection("jdbc:hive2://s101:10000/big12");
        Statement stm = con.createStatement();

        //插入
        stm.executeUpdate("insert into per values (3,'测试',23)");
        con.close();
        stm.close();
    }

    @Test
    public void del() throws  Exception{
        Connection con = DriverManager.getConnection("jdbc:hive2://s101:10000/big12");
        Statement stam = con.createStatement();
        int i = stam.executeUpdate("delete from per where id=1");
        System.out.println(i);

    }
}
