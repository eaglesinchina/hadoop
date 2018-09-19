package topk2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable,Text, Text,IntWritable> {
    //输出key-val:   ( 温度值 ,1)

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] ss = value.toString().split("\t");

        context.write( new Text(ss[1]),new IntWritable(1));
    }
}
