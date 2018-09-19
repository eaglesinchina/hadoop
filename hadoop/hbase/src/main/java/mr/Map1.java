package mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class Map1 extends Mapper<LongWritable,Text,   Text,IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //拆分行--》单词： 1
        StringTokenizer tokenizer = new StringTokenizer(value.toString());
       
       while (tokenizer.hasMoreTokens()){
           String s = tokenizer.nextToken();
           System.out.println(s);
           context.write(new Text(s),new IntWritable(1));
                   
       }
                

    }
}
