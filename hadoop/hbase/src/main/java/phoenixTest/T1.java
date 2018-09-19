package phoenixTest;

import org.junit.Test;

import java.sql.*;

public class T1 {

    @Test
    public void insert() throws ClassNotFoundException, SQLException {
//        org.apache.phoenix.jdbc.PhoenixDriver;
        String url="jdbc:phoenix:s102,s103,s104";
        String driver="org.apache.phoenix.jdbc.PhoenixDriver";

        //驱动，连接
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);

        PreparedStatement prest = conn.prepareStatement("upsert into users values (?,?)");

        for (int i=0;i<100;i++){
            prest.setInt(1,i+3);
            prest.setString(2,"lisi"+i);
            prest.addBatch();
        }
        prest.executeBatch();
        conn.commit();

        //
        prest.close();
        conn.close();
    }
    
    
    @Test
    public void get() throws ClassNotFoundException, SQLException {
         String url="jdbc:phoenix:s102,s103,s104";
         String driver="org.apache.phoenix.jdbc.PhoenixDriver";
         
         Class.forName(driver);
        Connection conn = DriverManager.getConnection(url);
        
        //创建prest
        Statement stam = conn.createStatement();
        String sql="select * from users";
        ResultSet resultSet = stam.executeQuery(sql);
        
        
        //遍历
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            System.out.println(id+"/"+name);
        }

        //关闭资源
        stam.close();
        conn.close();
    }
}
