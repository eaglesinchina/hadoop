package mr_sort.map_inputformat_maxtmp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable, Text,IntWritable> {
    int maxTmp=Integer.MIN_VALUE;

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //yaer, maxTmp
        for (IntWritable i: values){

            if(i.get()>maxTmp){
                maxTmp=i.get();
            }
        }

        //输出数据
        context.write(key, new IntWritable(maxTmp));

    }
}
