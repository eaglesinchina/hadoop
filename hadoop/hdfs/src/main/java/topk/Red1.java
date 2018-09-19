package topk;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable,  IntWritable, Text> {

    //统计个数    输出key,val( 23个, 温度值）
    int sum=0;
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        for (IntWritable value : values) {
            sum+=value.get();
        }

        context.write(new IntWritable(sum), new Text(key.toString()+" oc"));
//        context.write(new IntWritable(0), new Text("0 oc=========================="));
    }
}
