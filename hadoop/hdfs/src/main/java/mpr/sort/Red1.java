package mpr.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text, IntWritable,    Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //单词个数--单词

        //a [ 111 ]----->reduce:  a 3      -->out: 3 a
        int sum=0;
        for (IntWritable i: values){
            sum+=i.get();
        }

        context.write(key,new IntWritable(sum));
    }
}


