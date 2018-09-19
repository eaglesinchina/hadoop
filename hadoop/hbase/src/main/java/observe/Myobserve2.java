package observe;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Myobserve2 extends BaseRegionObserver {
   final  String fence_tab="weibo:fence";
    static final String guanzhu_tab = "weibo:guanzhu";

    static FileOutputStream out=null;
    static{
        try {
             out = new FileOutputStream("/home/centos/myobserve.log",true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
//        super.prePut(e, put, edit, durability);
        System.out.println("prePut. ...delete...999999...");
        try {
            out.write("prePut. ..delete...999999....".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        System.out.println("加入数据. .........");
        try {
            out.write("加入数据....".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //键值对：  row=a,   column='f1:name',   val=b
        RegionCoprocessorEnvironment env = e.getEnvironment();
        String tbname = env.getRegionInfo().getTable().getNameAsString();

        if(tbname.equalsIgnoreCase(guanzhu_tab)){
            //向：  关注表====》添加数据
            List<Cell> value = put.getFamilyCellMap().firstEntry().getValue();
            Cell cell = value.get(0);
            //a,   b
            String[] keys = Bytes.toString(put.getRow()).split(",");
            String time=keys[1];

            String val= Bytes.toString(CellUtil.cloneValue(cell));
            val=val+","+time;

            //向：  粉丝表---》插入数据
            Table table = (Table) env.getTable(TableName.valueOf(fence_tab));
            Put put1 = new Put(val.getBytes());//newkey

            put1.addColumn(  "f1".getBytes(),"name".getBytes(), keys[0].getBytes());

            table.put(put1);
            table.close();

        }
    }


    //==============
    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        //删除数据
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

                        //KEY: c2,1534255376535,   lisi
            //粉丝表：key==>(lisi,1534250643024,  c２)
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
