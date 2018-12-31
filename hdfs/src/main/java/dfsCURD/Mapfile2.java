package dfsCURD;

import org.apache.commons.math3.exception.MathIllegalNumberException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.IOException;

public class Mapfile2 {
    public static void main(String[] args) throws IOException {


        //1.创建Configuration
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        //2.获取FileSystem
        FileSystem fileSystem = FileSystem.get( conf);


        //3.创建文件输出路径Path
        Path path = new Path("/home/centos/txt/mapfile22");


        //4.new一个MapFile.Writer对象
        MapFile.Writer writer = new MapFile.Writer(conf, fileSystem, path.toString(), Text.class, Text.class);

        for(int i=9;i>0;i--){
            writer.append(new Text("name"+i), new Text("liaozhongmin"+i));
            System.out.println(i);
        }


        //5.调用MapFile.Writer.append()方法追加写入
        //关闭流
        IOUtils.closeStream(writer);
    }
    
    
    //读取数据
    @Test
    public void read() throws IOException {
        //1.创建Configuration
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        //2.获取FileSystem
        FileSystem fs = FileSystem.get( conf);


        MapFile.Reader reader = new MapFile.Reader(fs, "/home/centos/txt/mapfile22", conf);
        //创建空对象
        Text key = new Text();
        Text val= new Text();
        
        while(reader.next(key,val)){
            System.out.println(key+"===="+val);
        }
    }

    //merger
    @Test
    public void merge() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        MapFile.Merger merger = new MapFile.Merger(conf);

        Path[] ps={

              new Path("/home/centos/txt/mapfile"),
                new Path("/home/centos/txt/mapfile2"),
                new Path("/home/centos/txt/mapfile3"),

        };

        Path path = new Path("/home/centos/txt/mapfile-merge");

        merger.merge(ps,false, path);
    }

}
