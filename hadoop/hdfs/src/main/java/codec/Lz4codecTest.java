package codec;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Lz4codecTest {


    @Test
    public void t1() throws Exception {
        Configuration conf = new Configuration();

        //获取父类
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(Lz4Codec.class, conf);

        //压缩文件
        CompressionOutputStream out = codec.createOutputStream(new FileOutputStream("/home/centos/txt/a-lzop.txt"));
        FileInputStream in = new FileInputStream("/home/centos/txt/a.txt");
        IOUtils.copyBytes(in,out,1024);

        //
        out.close();
        in.close();
    }


    @Test//gzip
    public void t2() throws Exception {
        Configuration conf = new Configuration();

        //获取父类
        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(GzipCodec.class, conf);

        //压缩文件
//        CompressionOutputStream out = codec.createOutputStream(new FileOutputStream("/home/centos/txt/a-gzip.txt"));
//        FileInputStream in = new FileInputStream("/home/centos/txt/a.txt");
//        IOUtils.copyBytes(in,out,1024);

        CompressionInputStream in = codec.createInputStream(new FileInputStream("/home/centos/txt/a-gzip.txt"));
        int res=in.read();
        while (res!=-1){
            System.out.print((char)res);
            res=in.read();
        }

        //
//        out.close();
        in.close();
    }


//    @Test
//    public void t3(){
//        ReflectionUtils.newInstance()
//    }
}
