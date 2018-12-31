package mr_sort.map_inputformat_maxtmp;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 读取：  seqfile---->破解为： key-val:   传给reduce
 */
public class Map1 extends Mapper<NullWritable,Text, Text,IntWritable> {
    @Override
    protected void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException {
        //null---text(year,temp)
        String[] ss = value.toString().split("\t");

        context.write(new Text(ss[0]),  new IntWritable(Integer.parseInt( ss[1])));
    }
}
