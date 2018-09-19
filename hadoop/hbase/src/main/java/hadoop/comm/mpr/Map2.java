package hadoop.comm.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map2 extends Mapper<LongWritable,Text, Text,IntWritable> {

    int max=0;
    String word="";
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //输入---一行字符:  求出现最多的单词
        String[] words = value.toString().split("\t");
        int count=Integer.parseInt(words[1]);

        if(count>max) {
            max=count;
            word=words[0];
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        context.write(new Text(word), new IntWritable(max));
    }
}
