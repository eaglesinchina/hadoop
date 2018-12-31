package mr_sort.secondSort2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<TmpBean,NullWritable, Text,IntWritable> {

    @Override
    protected void reduce(TmpBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        //取出数据：  写出
//        String year=key.getYear();
//        int tmp=key.getTmp();
//
//        context.write(new Text(year), new IntWritable(tmp));


        for (NullWritable n: values){
            String year=key.getYear();
            int tmp=key.getTmp();

            context.write(new Text(year), new IntWritable(tmp));
        }


    }
}
