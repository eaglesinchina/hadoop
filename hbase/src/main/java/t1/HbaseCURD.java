package t1;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import sun.net.www.ParseUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

public class HbaseCURD {

    @Test
    public  void create() throws IOException {
        //获取配置
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        conf.set("fs.defaultFS","file:///");

        //创建： admin , table
        Admin admin = conn.getAdmin();

        //创建库，表
//        NamespaceDescriptor nmsdec =  NamespaceDescriptor.create("db1").build();
//        admin.createNamespace(nmsdec);

        HTableDescriptor tbdesc = new HTableDescriptor("db1:t2");
        tbdesc.addFamily(new HColumnDescriptor("f1"));
        admin.createTable(tbdesc);

        //关闭资源
        admin.close();
        conn.close();
    }

    @Test
    public  void insert() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));
        HTable table1 = (HTable) table;
        table1.setAutoFlush(false,false);

        //new put
        DecimalFormat format = new DecimalFormat("000");

        for (int i=0;i<100;i++){
            String str = format.format(i);
            Put put = new Put(Bytes.toBytes("row" + str));

            // addColumn(byte [] family, byte [] qualifier, byte [] value)
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"), Bytes.toBytes(str));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"), Bytes.toBytes(format.format(i%40)));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("tom"+str));
            table1.put(put);
        }

        //刷缓存
        table1.flushCommits();
        table1.close();
        table.close();
        conn.close();
    }

    @Test
    public  void delete() throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Delete del = new Delete(Bytes.toBytes("row000"));
        del.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("id"));
        del.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"));

        //关闭资源
        table.close();
        conn.close();
    }

    @Test
    public  void get() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));
        get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"));

        Result result = table.get(get);

        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            System.out.println(Bytes.toString(CellUtil.cloneRow(cell) )
                    +"/"+ Bytes.toString(CellUtil.cloneFamily(cell) )
                    +"/"+  Bytes.toString(CellUtil.cloneQualifier(cell))
                    +"/"+  Bytes.toString(CellUtil.cloneValue(cell)));

        }
        //关闭资源
        table.close();
        conn.close();

    }


    @Test
    public  void scan() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Scan scan = new Scan(Bytes.toBytes("row050"), Bytes.toBytes("row080"));
        scan.setCaching(10);
        scan.setBatch(10);
        //过滤字段
        scan.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));
        scan.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"));


        ResultScanner scanner = table.getScanner(scan);
        //变量数据
        Iterator<Result> it = scanner.iterator();
        while(it.hasNext()){
            List<Cell> cells = it.next().listCells();

            for (Cell cell : cells) {
                System.out.println(Bytes.toString(CellUtil.cloneRow(cell) )
                        +"/"+ Bytes.toString(CellUtil.cloneFamily(cell) )
                        +"/"+  Bytes.toString(CellUtil.cloneQualifier(cell))
                        +"/"+  Bytes.toString(CellUtil.cloneValue(cell)));
            }
        }
        //关闭资源
        table.close();
        conn.close();

    }
}
