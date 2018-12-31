package dfsCURD;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Sequence;
import java.io.IOException;

public class Mapfile {
    Configuration conf=null;
    FileSystem fs=null;
    Path p=null;


    @Before
    public void init() throws IOException {
        conf=new Configuration();
        p=new Path("/new-rand.txt");
        fs=FileSystem.get(conf);
    }

    @Test// mapfile.writer
    public void test() throws IOException {
        MapFile.Writer writer = new MapFile.Writer(conf, fs, "/home/centos/txt/hello-map",Text.class,NullWritable.class);
        SequenceFile.Reader reader = new SequenceFile.Reader(fs,p,conf);

        //创建空对象
        NullWritable val =NullWritable.get() ;
        Text key = new Text();


        while(reader.next(key,val)){
            writer.append(key,val);
            System.out.println(key+"--->  "+val);
        }

        //
        writer.close();
        reader.close();
    }
}
