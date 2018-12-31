package mr_sort.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red2 extends Reducer<Text, IntWritable,    IntWritable,Text> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //单词个数--单词

        //a [ 111 ]----->reduce:  a 3      -->out: 3 a
        int sum=0;
        for (IntWritable i: values){
            sum+=i.get();
        }

        context.write(new IntWritable(sum), key);
    }
}


