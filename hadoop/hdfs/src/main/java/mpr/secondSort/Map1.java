package mpr.secondSort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable, Text,  TmpBean, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //取出数据

        String[] data = value.toString().split("\t");
        String year=data[0];
        int tmp= Integer.parseInt(data[1]);


        context.write(new TmpBean(year,tmp), NullWritable.get());
    }
}
