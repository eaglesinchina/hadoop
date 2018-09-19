package callog;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.mapreduce.LoadIncrementalHFiles;

public class BuckLoad {
    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        //获取admin, table
        Admin admin = conn.getAdmin();
        Table table = conn.getTable(TableName.valueOf("db1:t2"));
        RegionLocator regionLocator = conn.getRegionLocator(TableName.valueOf("db1:t2"));


        LoadIncrementalHFiles loadFile = new LoadIncrementalHFiles(conf);
//doBulkLoad(Path hfofDir, final Admin admin, Table table,
//      RegionLocator regionLocator)
        loadFile.doBulkLoad(new Path("/t1_tb_data/f2"),
               admin,table,regionLocator );

        //关闭资源
        conn.close();
        table.close();
    }
    
}
