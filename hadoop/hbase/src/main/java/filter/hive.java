package filter;

import org.junit.Test;

import javax.sql.rowset.serial.SerialArray;
import java.sql.*;

public class hive {
    //驱动， url, username, pwd
    String driver="org.apache.hive.jdbc.HiveDriver";
//    String username="root";
//    String pwd="daitoue";
    String url="jdbc:hive2://s101:10000/big12";

    @Test
    public void get() throws ClassNotFoundException, SQLException {

        //驱动，连接
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, null, null);

        //获取stam
        Statement stam = conn.createStatement();
        ResultSet resultSet = stam.executeQuery("select * from stu");

        while (resultSet.next()){
            String name = resultSet.getString(1);
            int age = resultSet.getInt(2);
            String city = resultSet.getString(3);
            String friends = resultSet.getString(4);

            String date = resultSet.getString(5);

            System.out.println(name + "/" + age + "/" + city+"/"+friends+"/"+date);
        }

        //关闭资源
        stam.close();
        conn.close();
    }

    @Test
    public void create() throws Exception{
        Class.forName(driver);
        //conn
        Connection conn = DriverManager.getConnection(url, null, null);

        //prestam
        PreparedStatement prestam = conn.prepareStatement("create table test_hive(id int, name string, friends array<string>) " +
                "row format delimited fields terminated by ' '");

        int i = prestam.executeUpdate();
        System.out.println(i);

        //关闭
        prestam.close();
        conn.close();
    }

    @Test
    public void add() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        //conn
        Connection conn = DriverManager.getConnection(url, null, null);
       // PreparedStatement prest = conn.prepareStatement("insert into test_hive select ?,?,array('a','b')");

//        prest.setInt(1,1);
//        prest.setString(2,"lisi");
//        Array text = conn.createArrayOf("text", new String[]{"a", "b"});
//        prest.setArray(3,text);

        Statement stam = conn.createStatement();
        int i = stam.executeUpdate("insert into test_hive select 1,'lisi', array('a','b')");

        System.out.println(i);

        //关闭
        conn.close();
        stam.close();
    }
}
