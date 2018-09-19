package mpr.part;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable,Text,    Text,IntWritable> {
    static int count=1;

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //拆分单词，    单词数：  1
        String[] ss = value.toString().split(" ");

        for(String st: ss){
            context.write(new Text(st), new IntWritable(1));
            System.out.print("mapper...遍历string: "+st+", \t");
        }
        System.out.println("mapper......"+(count++)+"..........");
    }
}
