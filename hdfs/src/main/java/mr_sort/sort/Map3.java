package mr_sort.sort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map3 extends Mapper<Text,Text,    Passwd,NullWritable> {

    @Override//  afdasf  34
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //    单词数：  第一步的统计数

//        context.write( new IntWritable(Integer.parseInt(key.toString())),  value);
        context.write(new Passwd(value.toString(),Integer.parseInt(key.toString())),NullWritable.get());
    }
}
