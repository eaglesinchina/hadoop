package mysql2HDFS;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 读取：  seqfile---->破解为： key-val:   传给reduce
 */
public class Map1 extends Mapper<LongWritable,DbBean, Text,IntWritable> {
    @Override
    protected void map(LongWritable key, DbBean value, Context context) throws IOException, InterruptedException {
        //null---text(year,temp)

        String[] msg= value.getName().split(" ");

        context.write(new Text(msg[0]),  new IntWritable( 1  ));
    }
}
