package dfsCURD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Hdfs {
    //dfsCURD: 文件系统----》上传， 下载， 移动，复制， 删除  =====文件|目录

    //上传单个文件
    @Test
    public void putData() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        Path path = new Path("/fs-test/a.txt");
        if(! fs.exists(path)){

            fs.mkdirs(path);
            if(! fs.exists((path.getParent()))  ){
                fs.mkdirs(path.getParent());
            }
            fs.create(path,true);//不存在-->创建文件
        }

        //流： 拷贝
        FSDataOutputStream out = fs.create(new Path("/fs-test/a.txt"), true);
        FileInputStream in = new FileInputStream("/home/centos/wc.txt");

        IOUtils.copyBytes(in,out,1024);
       // 关闭资源
        out.close();
        in.close();
        fs.close();
    }

    //上传单个文件
    @Test
    public void putData2() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        fs.copyFromLocalFile(false, new Path("/home/centos/txt/word.txt"), new Path("/word.txt"));
        fs.close();
    }

    //上传-- 多个文件: 到一个文件夹中
    @Test
    public void putData3() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        Path path = new Path("/fs-test/all-file");
        if(! fs.exists(path)){

            fs.mkdirs(path);
        }

        //流： 拷贝
        fs.copyFromLocalFile(false,true,
                new Path[]{new Path("/home/centos/txt/a.xml"), new Path("/home/centos/txt/a.js")},
                path);
        fs.close();
    }


    //下载文件
    @Test
    public void get() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        fs.copyToLocalFile(false, new Path("/word.txt"), new Path("/home/centos/txt/word2.txt"));
        fs.close();
    }
    //下载文件
    @Test
    public void get2() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        FSDataInputStream in = fs.open(new Path("/word.txt"));
        FileOutputStream out = new FileOutputStream("/home/centos/txt/word3.txt");

        //流：拷贝
        IOUtils.copyBytes(in,out, 1024);
        in.close();
        out.close();
        fs.close();
    }


    //遍历文件夹
    @Test
    public void list() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //调用方法
        FSDataInputStream in = fs.open(new Path("/fs-test"));
        RemoteIterator<LocatedFileStatus> rmIter = fs.listFiles(new Path("/out"), true);
        
        while (rmIter.hasNext()){
            LocatedFileStatus status = rmIter.next();
            Path path = status.getPath();
            FSDataInputStream inputStream = fs.open(path);
            
            byte[] buf=new byte[1024];
            int len=0;
            
            while ((len = inputStream.read(buf))!=-1){
                System.out.println(new String(buf,0,len));
            }


        }
    }

    @Test
    public void move() throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.newInstance(conf);

        //移动文件
        fs.copyFromLocalFile(true,new Path("/word.txt"), new Path("/out"));
        FileUtil.copy(fs, new Path("/word.txt"),fs, new Path("/out"), true,conf);
        fs.close();

    }

}
