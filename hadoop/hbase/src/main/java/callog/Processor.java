package callog;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HRegionInfo;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Processor extends BaseRegionObserver {
    static FileOutputStream out = null;

    static {
        try {
            out = new FileOutputStream("/home/centos/callOb.log", true);
            out.write("start processor....\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) {
        //数据格式：   2，主叫电话,是否主叫1   ，时间，被叫电话，  通话时长            common (主叫电话， 被叫电话， 通话时间,通话时长)        profession
        //       5,   18923321001,1,  178934553454,13756780909
        try {
            out.write("put..\n".getBytes());
            out.flush();


            //1,解析数据
            String host_rowkey = Bytes.toString(put.getRow());

            String[] arrs = host_rowkey.split(",");
            String caller = arrs[1];
            long time = Long.parseLong(arrs[3]);
            String callee = arrs[4];
            int call_duration = Integer.parseInt(arrs[5]);

            //genkeyHost(String phone1, int host,String phone2,      long time, int call_duration){
            String custom_rowkey = Client.genkeyHost(callee, 0, caller, time, call_duration);
            RegionCoprocessorEnvironment env = e.getEnvironment();
            Table table = (Table) env.getTable(TableName.valueOf(Client.tbname));

            //主叫加入数据----》添加被叫信息
            if (host_rowkey.contains(",1,")) {
                //获取表， 添加数据

                Put put2 = new Put(Bytes.toBytes(custom_rowkey));
                put2.addColumn(Bytes.toBytes("common"), Bytes.toBytes("refId"), Bytes.toBytes(host_rowkey));
                table.put(put2);
            }
            //关闭
            table.close();
            out.write(("host_rowkey..-->" + host_rowkey).getBytes());
            out.write(("   custom_rowkey..-->" + custom_rowkey).getBytes());
            out.flush();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public boolean postScannerNext(ObserverContext<RegionCoprocessorEnvironment> e, InternalScanner s, List<Result> results, int limit, boolean hasMore) throws IOException {
        boolean b = super.postScannerNext(e, s, results, limit, hasMore);

        RegionCoprocessorEnvironment env = e.getEnvironment();
        Table table = env.getTable(TableName.valueOf(Client.tbname));
        List<Result> result2=results;
        List containor=new ArrayList();

        //判断是否：  被叫信息
        for (Result result : result2) {
            List<Cell> cells = result.listCells();


            for (Cell cell : cells) {
                String val = Bytes.toString(CellUtil.cloneValue(cell));
                ///判断是否：  被叫信息
                if (Bytes.toString(CellUtil.cloneRow(cell)).contains(",1,")) {
                        //替换result
                        results.clear();
                        byte[] rfid = CellUtil.cloneRow(cell);

                        //查询： 真实数据
                        Get get = new Get(rfid);
                        Result result1 = table.get(get);

                        //遍历数据： 替换rowkey
                        List<Cell> cells1 = result1.listCells();
                        for (Cell c : cells1) {

                            Cell cell1 = CellUtil.createCell(CellUtil.cloneRow(cell),
                                    CellUtil.cloneFamily(c), CellUtil.cloneQualifier(c),
                                    c.getTimestamp(), c.getTypeByte(), CellUtil.cloneValue(c)
                            );
                            containor.add(cell1);


                        }//内for

                        Result re = Result.create(containor);
                        results.add(re);
                }//for
            }//if refid---> 需要替换
        }

        return b;
    }
}
