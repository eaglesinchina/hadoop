package observe;

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
   static FileOutputStream out=null;
   final  String fence_tab="weibo:fence";
   final String guanzhu_tab = "weibo:guanzhu";

    static{
        try {
             out = new FileOutputStream("/home/centos/myobserve.log",true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        System.out.println("加入数据. .........");
            out.write("加入数据....".getBytes());
            out.flush();

        RegionCoprocessorEnvironment env = e.getEnvironment();
        String tbname = env.getRegionInfo().getTable().getNameAsString();

        if(tbname.equalsIgnoreCase(guanzhu_tab)){
            List<Cell> value = put.getFamilyCellMap().firstEntry().getValue();
            Cell cell = value.get(0);
            //输入key,val===>      a,   b
            String[] keys = Bytes.toString(put.getRow()).split(",");
            String time=keys[1];

            String val= Bytes.toString(CellUtil.cloneValue(cell));
            val=val+","+time;

            //向：  粉丝表---》插入数据  b,a
            Table table = (Table) env.getTable(TableName.valueOf(fence_tab));
            Put put1 = new Put(val.getBytes());
            put1.addColumn(  "f1".getBytes(),"name".getBytes(), keys[0].getBytes());

            table.put(put1);
            table.close();
        }
    }


    //==============
    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        //获取： 环境
        RegionCoprocessorEnvironment env = e.getEnvironment();

        //从哪个表删除数据
        String tbname = env.getRegionInfo().getTable().getNameAsString();
        if(tbname.equalsIgnoreCase(guanzhu_tab)) {

            out.write("删除数据...".getBytes());
            out.flush();
            //delete对象获取数据
            List<Cell> cells = delete.getFamilyCellMap().firstEntry().getValue();
            String val1= Bytes.toString(delete.getAttribute("value"));
            Cell cell = cells.get(0);

            //关注表：KEY: c2,1534255376535,      val: lisi
            //粉丝表：key==>(lisi,1534250643024,  val:c２)
            String key1 = Bytes.toString(CellUtil.cloneRow(cell));

            String[] keys = key1.split(",");
            String val = keys[0];
            String key = val1 + "," + keys[1];

            out.write(("输入key1..."+key1).getBytes());
            out.write(("输入val1..."+val1).getBytes());
            out.write(("输入key..."+key).getBytes());
            out.flush();
            //删除
            Delete delete1 = new Delete(Bytes.toBytes(key));
            delete1.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));

            Table table = (Table) env.getTable(TableName.valueOf("weibo:fence"));
            table.delete(delete1);

            //关闭资源
            table.close();
        }
    }
}
