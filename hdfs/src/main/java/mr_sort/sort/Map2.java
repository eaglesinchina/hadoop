package mr_sort.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map2 extends Mapper<Text,Text,    Text,IntWritable> {

    @Override//  afdasf  34
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //    单词数：  第一步的统计数

        context.write(key, new IntWritable(Integer.parseInt(value.toString())));
    }
}
