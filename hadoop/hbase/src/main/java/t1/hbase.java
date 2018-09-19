package test1;

import javolution.testing.JUnitContext;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class hbase {
    //创建数据库
    public static void main(String[] args) throws IOException {
        //创建： conf--配置
        Configuration conf = HBaseConfiguration.create();
        //连接工厂. 创建conn
        Connection conn = ConnectionFactory.createConnection(conf);
        //获取管理员
        Admin admin = conn.getAdmin();


        //创建数据库
        NamespaceDescriptor build = NamespaceDescriptor.create("test2").build();
        //管理员:  创建数据库
        admin.createNamespace(build);
        admin.close();
        conn.close();
    }

    //创建表
    public static void main2(String[] args) throws IOException {
        //创建： conf--配置
        Configuration conf = HBaseConfiguration.create();
        //连接工厂. 创建conn
        Connection conn = ConnectionFactory.createConnection();
        //获取管理员
        Admin admin = conn.getAdmin();


        //创建数据库
        TableName tab = TableName.valueOf("test2");
        HTableDescriptor tabdesc = new HTableDescriptor(tab);
        tabdesc.addFamily(new HColumnDescriptor("f1"));

        //管理员:  创建数据库
        admin.createTable(tabdesc);
        admin.close();
        conn.close();
    }

    //获取表数据
    public static void main3(String[] args) throws IOException {
        //创建： conf--配置
        Configuration conf = HBaseConfiguration.create();
        //连接工厂. 创建conn
        Connection conn = ConnectionFactory.createConnection();
        //获取管理员
        Admin admin = conn.getAdmin();


        //创建数据库
        TableName tab = TableName.valueOf("test1");


        //管理员:  创建数据库
//        admin.createTable(tabdesc);
        admin.close();
        conn.close();
    }

}
