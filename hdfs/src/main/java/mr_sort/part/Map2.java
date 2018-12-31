package mr_sort.part;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map2 extends Mapper<LongWritable,Text,    Text,IntWritable> {
    static int count=1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //value数据：    单词--次数

        //1,截取： 单词 ////解除后缀：
        String[] msg = value.toString().split("\t");
        String word = msg[0];



        //2,截取： 次数
        int count = Integer.parseInt(msg[1]);

        //3，写入数据
        context.write(new Text(word),new IntWritable(count));
        System.out.println("second map..............................."+(count++));

    }

}
