package mpr.part;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable,     Text,IntWritable> {
    static  int count=1;
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //合并该数据（key-单词）---->[1,1,1,1]: 个数总和

        System.out.println("......reduce....."+(count++)+"......");
        int sum=0;
        for (IntWritable i:  values){
            sum+=i.get();
        }

        context.write(key, new IntWritable(sum));
    }
}
