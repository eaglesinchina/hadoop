package observe2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class ClientPutRm {
    static final String hbase_tab = "weibo:user_info";

    //添加数据：   a,    guanzhu:name,     val=b,
    //            b,   fence:name,val=a

    ////put:     'a','guanzhu:lisi','null'
    //        // del:     a,  guanzhu,name
    public static void put(String key, String val) throws IOException {
        //配置
        Configuration conf = HBaseConfiguration.create();
        //连接conn
        Connection conn = ConnectionFactory.createConnection(conf);
        //管理员

        //表
        Put put = new Put(key.getBytes());
        HTable table = (HTable) conn.getTable(TableName.valueOf(hbase_tab));
        put.addColumn("guanzhu".getBytes(), val.getBytes(), "1".getBytes());

        table.put(put);
        table.close();
        conn.close();

    }

    public static void delete(String key,String val) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf(hbase_tab));

        //put:     'a','guanzhu:lisi','null'
        // del:     a,  guanzhu,lisi
        Delete delete = new Delete(key.getBytes());
        delete.addColumn("guanzhu".getBytes(), val.getBytes());
        table.delete(delete);

        //关闭资源
        table.close();
        conn.close();

    }

    //插入数据
    public static void main(String[] args) throws Exception {
//        put("992", "h3");

        delete("992", "h3");
    }

}
