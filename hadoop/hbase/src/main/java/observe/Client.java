package observe;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.io.IOException;

public class Client {
    static final String guanzhu_tab = "weibo:guanzhu";

    //添加数据
    public static void put(String key, String val) throws IOException {
        //配置
        Configuration conf = HBaseConfiguration.create();
        //连接conn
        Connection conn = ConnectionFactory.createConnection(conf);
        //表
        key = key + "," + System.currentTimeMillis();
        Put put = new Put(key.getBytes());

        HTable table = (HTable) conn.getTable(TableName.valueOf(guanzhu_tab));
        put.addColumn("f1".getBytes(), "name".getBytes(), val.getBytes());

        table.put(put);
        table.close();
        conn.close();
    }

    public static void delete(String key,String val) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf("weibo:guanzhu"));

        //查询： 完整key,  val
        Scan scan = new Scan();
        //   过滤器=   行 + 单列值
        //c,2343454 --->取消： llisi
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator(key));
        SingleColumnValueFilter sing = new SingleColumnValueFilter("f1".getBytes(), "name".getBytes(),
                CompareFilter.CompareOp.EQUAL, new BinaryComparator(val.getBytes()));
        FilterList filter = new FilterList(rowFilter, sing);
        scan.setFilter(filter);
//        scan.setCacheBlocks()

        ResultScanner result = table.getScanner(scan);
        Cell cell = result.next().listCells().get(0);

        //取出key
        String realKey = Bytes.toString(CellUtil.cloneRow(cell));

        //c,1534253779035 --->取消李四
        Delete delete = new Delete(realKey.getBytes());
        delete.addColumn("f1".getBytes(), "name".getBytes());
        delete.setAttribute("value",val.getBytes());
        table.delete(delete);

        //关闭资源
        table.close();
        conn.close();

    }

    //插入数据
    public static void main(String[] args) throws Exception {
        put("c6", "lisi6");
  //      delete("c6","lisi6");
    }

}
