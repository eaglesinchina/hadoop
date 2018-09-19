package observe;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.apache.hadoop.hbase.regionserver.Store;
import org.apache.hadoop.hbase.regionserver.StoreFile;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Myobserve extends BaseRegionObserver {
   static FileOutputStream out=null;
    static{
        try {
             out = new FileOutputStream("/home/centos/myobserve.log",true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws  Exception{
        new Myobserve().postDelete(new ObserverContext<RegionCoprocessorEnvironment>(),null,null,null);
        System.out.println("main......");
    }
    @Override
    public void preOpen(ObserverContext<RegionCoprocessorEnvironment> e) throws IOException {
        super.preOpen(e);
        System.out.println("open. 前.........");
        out.write("open. 前.........".getBytes());
        out.flush();
    }

    @Override
    public void postOpen(ObserverContext<RegionCoprocessorEnvironment> e) {
        super.postOpen(e);
        System.out.println("open 后.....");
        try {
            out.write("open. 后.........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public InternalScanner preFlush(ObserverContext<RegionCoprocessorEnvironment> e, Store store, InternalScanner scanner) throws IOException {
        System.out.println("preFlush. 前.........");
        try {
            out.write("preFlush. 前.........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return super.preFlush(e, store, scanner);
    }

    @Override
    public void postFlush(ObserverContext<RegionCoprocessorEnvironment> e, Store store, StoreFile resultFile) throws IOException {
        System.out.println("preFlush. 后.........");
        super.postFlush(e, store, resultFile);
        try {
            out.write("postFlush. 前.........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.prePut(e, put, edit, durability);
        System.out.println("prePut. .........");
        try {
            out.write("prePut. .........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void postPut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        super.postPut(e, put, edit, durability);
        System.out.println("postPut. .........");
        try {
            out.write("postPut. .........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void preDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        super.preDelete(e, delete, edit, durability);
        System.out.println("preDelete. .........");
        try {
            out.write("preDelete. .........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void postDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        super.postDelete(e, delete, edit, durability);
        System.out.println("postDelete. .........");
        try {
            out.write("postDelete. .........".getBytes());
            out.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
