package mr_sort.map_inputformat_maxtmp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.BufferedReader;
import java.io.FileReader;

public class MkSeq {
    //生成： seqfile

    public static void mkSeq() throws Exception {
        //输入流
        BufferedReader br = new BufferedReader(new FileReader("/home/centos/txt/temp.txt"));


        //输出流
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);

        Path pathout = new Path("/home/centos/txt/tmp-out-seq");
        SequenceFile.Writer writer1 = SequenceFile.createWriter(fs, conf, pathout, NullWritable.class, Text.class);

        //生成数据
        String line=null;
        while((line=br.readLine())!=null){
            writer1.append(NullWritable.get(), new Text(line));
        }

        writer1.close();
        br.close();

    }
}
