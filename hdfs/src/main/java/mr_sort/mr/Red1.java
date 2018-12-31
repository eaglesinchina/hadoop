package hadoop.comm.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Red1 extends Reducer<Text,IntWritable,  Text,IntWritable> {


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //输入---一行字符:  求出现最多的单词

        int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();
        }

            context.write(new Text(key.toString()), new IntWritable(sum));
    }


}
