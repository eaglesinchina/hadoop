package mysql2HDFS;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable, Text,IntWritable> {
    int maxTmp=Integer.MIN_VALUE;

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //yaer, maxTmp
        int sum=0;
        for (IntWritable i: values){

            sum+=i.get();
        }

        //输出数据
        context.write(key, new IntWritable(sum));

    }
}
