package mr_sort.sort;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<Text,Text,    Text,IntWritable> {
    static int count=1;

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        //拆分单词，    单词数：  1
//       context.write(key,new IntWritable(Integer.parseInt(value.toString())));
//        System.out.println("mapper......"+(count++)+"..........");

        //数据： 密码  --编号
        //统计密码使用次数: text--int
        context.write(key, new IntWritable(1));
    }
}
