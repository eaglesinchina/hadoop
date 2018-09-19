package topk;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable,Text, Text,IntWritable> {
    //输出key-val:   ( 温度值 ,1)

    MultipleOutputs out=null;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        out=new MultipleOutputs(context);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] ss = value.toString().split("\t");

        context.write( new Text(ss[1]),new IntWritable(1));

        out.write("text", new Text("a"),new IntWritable(1), "/home/centos/test/txt");
        out.write("seq", new Text("a"), new IntWritable(1), "/home/centos/test/seq");
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        out.close();
    }
}
