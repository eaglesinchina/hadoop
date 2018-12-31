package dfsCURD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class Seqfile {
    Path p=null;
    Configuration conf=null;
    FileSystem fs=null;//读取伪分布式：  hdfs文件

    @Before
    public void init() throws IOException {
        p=new Path("/samp-out.txt");
//        p=new Path("/new-rand.txt");
//        p=new Path("/new-hello.txt");
        conf=new Configuration();
        fs = FileSystem.get(conf);
    }

    //writer
    @Test
    public void test1() throws IOException {
        SequenceFile.Writer writer = SequenceFile.createWriter(fs, conf, p,  Text.class,NullWritable.class, SequenceFile.CompressionType.BLOCK);

        for(int i=0;i<10;i++){
            int ran= (int) (Math.random()*i*10);//1-9  *i
            writer.append(new Text(ran+"hello..."),NullWritable.get());
        }

        //
        writer.close();
    }


    //reader
    @Test
    public void test2() throws IOException {
        SequenceFile.Reader reader = new SequenceFile.Reader(fs, p, conf);
        
        //创建空对象
        NullWritable val = NullWritable.get();
        Text key = new Text();
        
        //遍历读取内容
        while( reader.next(key,val)){
            System.out.println(key+"--------->  "+val);
            
        }
        //
        IOUtils.closeStream(reader);
       
    }


    //sorter
    @Test
    public void test3() throws IOException {
        SequenceFile.Sorter sorter = new SequenceFile.Sorter(fs,Text.class,NullWritable.class,conf);
        sorter.sort(p,new Path("/new-hello.txt"));
    }

}
