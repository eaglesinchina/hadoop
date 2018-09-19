package totalsort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class PassMapper extends Mapper<Text,Text,Text,IntWritable> {

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {


        context.write(key,new IntWritable(Integer.parseInt(value.toString())));


    }
}
