package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FS {
    //属性
    static Configuration conf;
    static FileSystem fs;

    static  {
        conf=new Configuration();

        try {
            fs=FileSystem.get(conf);
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) throws Exception {
        //测试hdfs: 文件上传， 下载： 过程
        //upload();
        download();

    }


    //上传文件
    private static void upload() throws IOException {
        FSDataOutputStream out = fs.create(new Path("/upload4.txt"));
        FileInputStream in = new FileInputStream("/home/centos/txt/a.txt");
        
        IOUtils.copyBytes(in,out,1024);
        out.close();
        in.close();
        System.out.println("upload over...");

        org.apache.hadoop.hdfs.server.namenode.NameNode;

    }

    //下载文件
    private static void download() throws Exception {
        FSDataInputStream in = fs.open(new Path("/upload.txt"));
        FileOutputStream out = new FileOutputStream("/home/centos/txt/down.txt");

        IOUtils.copyBytes(in,out,1024);
        
        //关闭资源
        out.close();
        in.close();
        System.out.println("download over...");
    }
}
