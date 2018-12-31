package observe2;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Myobserve3 extends BaseRegionObserver {
    static final String hbase_tab = "weibo:user_info";
    static FileOutputStream out = null;

    static {
        try {
            out = new FileOutputStream("/home/centos/myobserve3.log", true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        System.err.println("postPut. ...");

        //键值对：  row=a,   column='f1:name',   val=b

        ////put:     'a','guanzhu:lisi','null'
        //        // del:     a,  guanzhu,name
        RegionCoprocessorEnvironment env = e.getEnvironment();
        String tbname = env.getRegionInfo().getTable().getNameAsString();
        out.write("put..\n".getBytes());
        out.flush();


        //向：  关注表====》添加数据
        List<Cell> value = put.getFamilyCellMap().firstEntry().getValue();
        Cell cell = value.get(0);
        //a,   b
        String key = Bytes.toString(CellUtil.cloneRow(cell));
        String val = Bytes.toString(CellUtil.cloneQualifier(cell));
        String family = Bytes.toString(CellUtil.cloneFamily(cell));

        out.write(("put..family\n---"+family).getBytes());
        out.flush();
        Table table = (Table) env.getTable(TableName.valueOf(hbase_tab));
        if (tbname.equalsIgnoreCase(hbase_tab) && "guanzhu".equalsIgnoreCase(family)) {


            //向：  粉丝表---》插入数据
            Put put1 = new Put(val.getBytes());//newkey

            put1.addColumn(Bytes.toBytes("fence"), Bytes.toBytes(key), Bytes.toBytes("null"));

            table.put(put1);

        }
        table.close();

    }


    //==============
    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        System.err.println("postDelete. ...");
        out.write("delete..\n".getBytes());
        out.flush();

        //获取： 环境
        // del:     a,  guanzhu：lisi
        RegionCoprocessorEnvironment env = e.getEnvironment();

        //从哪个表删除数据
        String tbname = env.getRegionInfo().getTable().getNameAsString();

        //delete对象获取数据
        List<Cell> cells = delete.getFamilyCellMap().firstEntry().getValue();
        Cell cell = cells.get(0);

        String key = Bytes.toString(CellUtil.cloneRow(cell));
        String val = Bytes.toString(CellUtil.cloneQualifier(cell));
        String family = Bytes.toString(CellUtil.cloneFamily(cell));

        out.write(("delete..family\n---"+family).getBytes());
        out.flush();
        Table table = (Table) env.getTable(TableName.valueOf(hbase_tab));
        if (tbname.equalsIgnoreCase(hbase_tab) && "guanzhu".equalsIgnoreCase(family)) {

            //删除
            Delete delete1 = new Delete(Bytes.toBytes(val));
            delete1.addColumn(Bytes.toBytes("fence"), Bytes.toBytes(key));

            table.delete(delete1);
        }
        //关闭资源
        table.close();
    }
}
