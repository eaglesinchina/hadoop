package hadoop;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Hive {
    @Test
    public void create() throws ClassNotFoundException, SQLException {
        //driver
       String driver="org.apache.hive.jdbc.HiveDriver";
       String url="jdbc:hive2://localhost:10000/db1" ;

       Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        
        //创建数据库，表
        Statement stam = conn.createStatement();
//        int res = stam.executeUpdate("create database db2");
//         stam.execute("create table t1(id int, name string)");
        int res = stam.executeUpdate("insert into per values(1,'lisi')");

        System.out.println(res);

        //关闭资源i
        conn.close();


    }

}
