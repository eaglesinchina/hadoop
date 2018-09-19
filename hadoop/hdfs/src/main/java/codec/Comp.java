package codec;

import org.anarres.lzo.LzoCompressor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;
import org.junit.Test;
import sun.nio.ch.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Comp {


    @Test
    public void test() throws Exception {
        //创建编解码器
        Class[] cs={GzipCodec.class,
                     Lz4Codec.class,

                SnappyCodec.class,
                        BZip2Codec.class,
                DeflateCodec.class,
                DefaultCodec.class
        };
        Configuration conf = new Configuration();

        for (Class c: cs){
            CompressionCodec cod = (CompressionCodec) ReflectionUtils.newInstance(c,conf);
            encode(cod, "/home/centos/txt/a.txt","/home/centos/txt/deco/a"+cod.getDefaultExtension());
        }





    }

    //压缩： 序列化
    public void encode(CompressionCodec cod,String pathin, String pathout) throws Exception {
        long start = System.currentTimeMillis();

        CompressionOutputStream out = cod.createOutputStream(new FileOutputStream(pathout));
        FileInputStream in = new FileInputStream(pathin);

        IOUtils.copyBytes(in,out,1024);

        out.close();
        in.close();
        File f = new File(pathout);
        System.out.println(cod.getDefaultExtension()+"  time==> "+(System.currentTimeMillis()-start)+"文件大小==》"+f.length());


    }

    //解压缩： 反序列化
    public void decode(CompressionCodec cod, String pathin,String pathout) throws Exception {
        CompressionInputStream in = cod.createInputStream(new FileInputStream(pathin));
        FileOutputStream out = new FileOutputStream(pathout);

        IOUtils.copyBytes(in,out,1024);

        in.close();
        out.close();

    }
}
