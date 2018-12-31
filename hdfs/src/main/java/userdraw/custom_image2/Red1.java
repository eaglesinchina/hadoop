package userdraw.custom_image2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text, IntWritable,  Text,NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //相同：  uid_appid  --->使用时间汇总（求和）
        int sum=0;

        for (IntWritable value : values) {
            sum+=value.get();

        }

        //写出数据
        context.write(new Text(key.toString()+"|"+ sum), NullWritable.get());
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}
