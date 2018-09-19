package filter;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HbaseTest {
    Connection conn=null;

    @Before
    public void before() throws IOException {
         Configuration conf = HBaseConfiguration.create();
         conn = ConnectionFactory.createConnection(conf);

    }

    @Test
    public  void create() throws IOException {
        //创建库， 表
        Admin admin = conn.getAdmin();

        NamespaceDescriptor db1 = NamespaceDescriptor.create("db1").build();
        admin.createNamespace(db1);
        HTableDescriptor tbs = new HTableDescriptor(TableName.valueOf("db1:t1"));

        HColumnDescriptor f1 = new HColumnDescriptor("f1");
        HColumnDescriptor f2 = new HColumnDescriptor("f2");
        tbs.addFamily(f1);
        tbs.addFamily(f2);

        admin.createTable(tbs);

        conn.close();
        admin.close();
    }
    @Test
    public  void add() throws IOException {//增加，覆盖
        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"), Bytes.toBytes("lisi2"));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("sex"), Bytes.toBytes("男"));
        put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("city"), Bytes.toBytes("北京"));

        table.put(put);
        //关闭资源
        table.close();
    }

    @Test
    public  void add2() throws IOException {//批量增加数据
        //获取表
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        ArrayList<Put> puts = new ArrayList<Put>();
        for (int i=2;i<100;i++){
            DecimalFormat format = new DecimalFormat("000");

            Put put = new Put(Bytes.toBytes("row"+format.format(i)));
            put.addColumn(Bytes.toBytes("f"+format.format(i)), Bytes.toBytes("age"), Bytes.toBytes(format.format(i%50)+""));
            put.addColumn(Bytes.toBytes("f2"), Bytes.toBytes("country"), Bytes.toBytes(format.format(i)+"国"));
            put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("sex"), Bytes.toBytes(i%2==0?"男":"女"));
           put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("city"), Bytes.toBytes(format.format(i)+"city"));
            puts.add(put);
            put.setDurability(Durability.SKIP_WAL);
        }

        HTable tab = (HTable) table;
        tab.setAutoFlush(false,true);

        table.put(puts);
        //关闭资源
        table.close();
    }

    @Test
    public void del() throws IOException {//删除一个： 旧的版本数据
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Delete delete = new Delete(Bytes.toBytes("row1"));
        delete.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));

        table.delete(delete);
    }

    @Test
    public void get() throws IOException {
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Get get = new Get(Bytes.toBytes("row1"));
        get.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));  //取出qualifier---》val
       //  get.addFamily(Bytes.toBytes("f1"));//取出列族的--->val

        Result result = table.get(get);
        List<Cell> cells = result.listCells();
        //
        for (Cell c: cells){
            String row = Bytes.toString(CellUtil.cloneRow(c));
            String family = Bytes.toString(CellUtil.cloneFamily(c));

            String qual = Bytes.toString(CellUtil.cloneQualifier(c));
            String val = Bytes.toString(CellUtil.cloneValue(c));

            System.out.println(row+"/"+family+"/"+qual+"/"+val);

        }
    }

    @Test
    public void scan() throws IOException {
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("age"));
        scan.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("name"));


        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> it = scanner.iterator();
        while (it.hasNext()){
            Result result = it.next();
            List<Cell> cells = result.listCells();

            Iterator<Cell> iter = cells.iterator();
            while(iter.hasNext()){
                Cell c= iter.next();

                String row = Bytes.toString(CellUtil.cloneRow(c));
                String family = Bytes.toString(CellUtil.cloneFamily(c));

                String qual = Bytes.toString(CellUtil.cloneQualifier(c));
                String val = Bytes.toString(CellUtil.cloneValue(c));

                System.out.println(row+"/"+family+"/"+qual+"/"+val);
            }

        }
    }

    @Test//过滤： age<30
    public void scan2() throws IOException {
        Table table = conn.getTable(TableName.valueOf("db1:t1"));

        Scan scan = new Scan();
//        scan.addFamily(Bytes.toBytes("f1"));
//        scan.addFamily(Bytes.toBytes("f2"));
//        scan.setBatch(9);//默认扫描整个对象： 同一个row
        scan.setCaching(10);//一次扫描几个对象
        //scan.setFilter(new RowFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("row030"))));//前30行
//        scan.setFilter(new FamilyFilter(CompareFilter.CompareOp.EQUAL,  new BinaryComparator(Bytes.toBytes("f1"))));//过滤列族
//        scan.setFilter(new QualifierFilter(CompareFilter.CompareOp.EQUAL,  new RegexStringComparator("age")));
//        scan.setFilter(new ValueFilter(CompareFilter.CompareOp.LESS_OR_EQUAL,  new BinaryComparator(Bytes.toBytes("030"))));

        /*QualifierFilter quafilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("age"));
        ValueFilter valf = new ValueFilter(CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes("030")));
        FamilyFilter famfilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f1")));
        FilterList flist = new FilterList(quafilter, valf,famfilter);*/

        //值： 过滤--->显示同一列族值
        SingleColumnValueFilter singleF = new SingleColumnValueFilter(Bytes.toBytes("f1"), Bytes.toBytes("age"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("020")));
        FamilyFilter famfilter = new FamilyFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("f1")));

//        FilterList flist = new FilterList( singleF,famfilter);
        FilterList flist = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        flist.addFilter(singleF);
        flist.addFilter(famfilter);

        scan.setFilter(flist);
        ResultScanner scanner = table.getScanner(scan);
        Iterator<Result> it = scanner.iterator();
        while (it.hasNext()) {
            Result result = it.next();
            List<Cell> cells = result.listCells();

            Iterator<Cell> iter = cells.iterator();
            while (iter.hasNext()) {
                Cell c = iter.next();

                String row = Bytes.toString(CellUtil.cloneRow(c));
                String family = Bytes.toString(CellUtil.cloneFamily(c));

                String qual = Bytes.toString(CellUtil.cloneQualifier(c));
                String val = Bytes.toString(CellUtil.cloneValue(c));

                System.out.println(row + "/" + family + "/" + qual + "/" + val);
            }
            System.out.println("===========");
        }
    }
}
