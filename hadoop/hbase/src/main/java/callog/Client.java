package callog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.DU;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class Client {
    static final String tbname="weibo:call_log";

    public static void main(String[] args) throws IOException {                     //列族
        //数据格式：   2，主叫电话,是否主叫1   ，时间，被叫电话，通话时长              common (主叫电话， 被叫电话， 通话时间,通话时长)        profession

//        scan();
        String caller="18923321001";
        String callee="13756780909";
        long time=178934553454L;
        int call_duration=2345;

        String host_rowkey = genkeyHost(caller, 1, callee, time, call_duration);
        //插入数据
        putData(host_rowkey,caller,callee,  time,call_duration);

        System.out.println(host_rowkey);
    }

    private static void putData(String rowkey, String caller, String callee, long time, int call_duration) throws IOException {
        //配置， 连接
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取表
        Table table = conn.getTable(TableName.valueOf(tbname));

        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes("common"), Bytes.toBytes("caller"), Bytes.toBytes(caller));
        put.addColumn(Bytes.toBytes("common"), Bytes.toBytes("callee"), Bytes.toBytes(callee));
        put.addColumn(Bytes.toBytes("common"), Bytes.toBytes("time"), Bytes.toBytes(time+""));
        put.addColumn(Bytes.toBytes("common"), Bytes.toBytes("call_duration"), Bytes.toBytes(call_duration+""));
        table.put(put);

        //关闭
        table.close();
        conn.close();
    }

    public static String genkeyHost(String phone1, int host,String phone2,     long time, int call_duration){

        //分区号： 电话尾号3454+ 日期% 10
        long partNo=(Integer.parseInt(phone1.substring(6))+time)%10;
        String rowid=partNo+","+phone1
                                    +","+host+","+time+"," +phone2+","+call_duration;
        return rowid;
    }

    public static void scan() throws IOException {
        //配置， 连接
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf(tbname));

        //获取scan:  扫描表
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("common"));
        ResultScanner scanner = table.getScanner(scan);

        //遍历结果
        for (Result result : scanner) {
            List<Cell> cells = result.listCells();

            for (Cell cell : cells) {
                System.out.println(Bytes.toString(CellUtil.cloneQualifier(cell))+ Bytes.toString((CellUtil.cloneValue(cell))));

            }

        }


    }

}
