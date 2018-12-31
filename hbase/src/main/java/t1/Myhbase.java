package t1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class Myhbase {

    //创建数据库
    public static void main1(String[] args) throws IOException {
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
        TableName tab = TableName.valueOf("test2:t2");
        HTableDescriptor tabdesc = new HTableDescriptor(tab);
        tabdesc.addFamily(new HColumnDescriptor("f1"));
        tabdesc.addFamily(new HColumnDescriptor("f2"));
        //管理员:  创建数据库
        admin.createTable(tabdesc);
        admin.close();
        conn.close();
    }
    //添加数据
    public static void main3(String[] args) throws IOException {
        //配置
        Configuration conf = HBaseConfiguration.create();

        //连接conn
        Connection conn = ConnectionFactory.createConnection(conf);


        //管理员
        Admin admin = conn.getAdmin();


        //表
        HTable table = (HTable)conn.getTable(TableName.valueOf("test2:t2"));
        table.setAutoFlush(false,false);

        for (int i=0;i<1000;i++){
            //put对象
            Put put = new Put(("row"+i).getBytes());
            put.addColumn("f1".getBytes(),"id".getBytes(),(i+"").getBytes());
            put.addColumn("f1".getBytes(),"name".getBytes(),("name"+i).getBytes());
            put.addColumn("f2".getBytes(),"sex".getBytes(),("sex"+ i%2).getBytes());
            put.addColumn("f2".getBytes(),"age".getBytes(),("val"+i%100).getBytes());

            table.put(put);
        }
        table.flushCommits();
        //
        admin.close();
    }

    //获取表数据
    public static void main4(String[] args) throws IOException {
        //创建： conf--配置
        Configuration conf = HBaseConfiguration.create();
        //连接工厂. 创建conn
        Connection conn = ConnectionFactory.createConnection(conf);
        //获取管理员
        Admin admin = conn.getAdmin();


        //创建数据库
        TableName tab = TableName.valueOf("test2:t2");
        Table table = conn.getTable(tab);

        //获取scan对象
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            List<Cell> cells = result.listCells();
            for (Cell c: cells){
              ;
                System.out.println( Bytes.toString(CellUtil.cloneRow(c))+"/"
                        +Bytes.toString(CellUtil.cloneFamily(c))
                        + "/" + Bytes.toString(CellUtil.cloneQualifier(c))+
                        "/" + Bytes.toString(CellUtil.cloneValue(c) ));
            }
            System.out.println();

        }
        //管理员:  创建数据库
//        admin.createTable(tabdesc);
        admin.close();
        conn.close();
    }

    //获取单个数据
    public static void main(String[] args) throws IOException {
        //配置信息
        Configuration conf = HBaseConfiguration.create();
        //连接对象
        Connection conn = ConnectionFactory.createConnection(conf);

        //管理员
        Admin admin = conn.getAdmin();
        //获取表
        Table table = conn.getTable(TableName.valueOf("test2:t2"));
        Result result = table.get(new Get("row1".getBytes()));

        List<Cell> cells = result.listCells();
        if (cells!=null)
        for (Cell cell : cells) {
            System.out.println(
                   Bytes.toString( CellUtil.cloneRow(cell))+"/"
                    +Bytes.toString(CellUtil.cloneFamily(cell))+"/"
                    + Bytes.toString(CellUtil.cloneQualifier(cell))+"/"
                    + Bytes.toString(CellUtil.cloneValue(cell))
            );

        }

    }
}
