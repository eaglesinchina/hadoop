package hadoop.comm.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable,Text, Text,IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //输入---一行字符:  求出现最多的单词
        String[] words = value.toString().split(" ");

        for (String word : words) {
            context.write(new Text(word), new IntWritable(1));

        }
    }
}
