package test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HDFSfile {
    Configuration conf=null;
    FileSystem fs=null;

    @Before
    public void init() throws IOException {
        conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        fs = FileSystem.get(conf);

        System.out.println("init......\n\n");
    }
    
   // @Test
    public void test1(Path p1) throws IOException {//测试  dfs  api:  文件读写
        //读文件
      //  Path p1 = new Path("/home/centos/txt/a.txt");
        System.out.println(p1.getName());
        FSDataInputStream in = fs.open(p1);

        byte[] buf=new byte[1024];
        int len=0;

        while((len=in.read(buf))!=-1){
            System.out.print(new String(buf,0,len));
        }
    }
    
    @Test
    public void test2() throws IOException {//测试  dfs  api:  文件读写----递归读取文件
        recusiveReadDir(new Path("/home/centos/txt"));
    }
    public void recusiveReadDir(Path p) throws IOException {
        FileStatus[] ss = fs.listStatus(p);
        for(FileStatus s: ss){
            if(!s.isFile()){

                String name = s.getPath().getParent().getName();
                String name2 = s.getPath().getName();


                Path sonDir = new Path(p, name2);
                recusiveReadDir(sonDir);
                System.out.println("pname---"+name+"  dirname--"+name2);

            }else{
                test1(s.getPath());//读取文件内容
            }
        }
    }

    //删除文件
    @Test
    public void test3() throws IOException {
        boolean delete = fs.delete(new Path("/home/centos/txt/a"), true);
        System.out.println("--->"+ delete);
    }

    //修改文件
    @Test
    public void test4() throws IOException {
        //FSDataOutputStream out = fs.append(new Path("/home/centos/txt/a.txt"));
        Path parent = new Path("/home/centos/txt");
//        Path p1 = new Path(parent, "a.txt");
//        Path p2 = new Path(parent, "a2.txt");
        Path p3 = new Path(parent, "a.txt");
        fs.rename(p3,new Path(parent,"a.new.txt"));

//        Path p32 = new Path(parent, "3-1dir");
//
//        fs.concat(p3,new Path[]{p1,p2});

//        fs.copyFromLocalFile(p1,p3);
//        fs.copyFromLocalFile(true,true,new Path[]{p1,p2},p32);


        //
    }

    



}
