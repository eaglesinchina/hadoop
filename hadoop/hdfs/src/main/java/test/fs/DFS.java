package test.fs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class DFS {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);


        //写入的位置
        FSDataOutputStream out = fs.create(new Path("/test.txt"));
        //输入路径
        FileInputStream in = new FileInputStream("/home/centos/txt/a.txt");


        //资源复制
        IOUtils.copyBytes(in,out,1024);

        //
        out.close();
        in.close();

    }


}
