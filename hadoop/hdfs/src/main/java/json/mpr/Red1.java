package json.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable,  Text,IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        //75144086_效果赞 1,   75144086_无推销 23 , 75144086_无办卡 43


        //统计：  评论总数
        int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();

        }

        context.write(key, new IntWritable(sum));
    }
}
