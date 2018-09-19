package db2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable, DbBean2,NullWritable> {
    int maxTmp=Integer.MIN_VALUE;
    int index=1;

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //yaer, maxTmp
        int sum=0;
        for (IntWritable i: values){

            sum+=i.get();
        }

        //输出数据
        String word = key.toString();
        DbBean2 bean = new DbBean2( index++ ,word, sum);
        context.write(bean, NullWritable.get());

    }
}
