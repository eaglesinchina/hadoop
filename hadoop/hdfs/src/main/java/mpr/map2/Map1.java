package mpr.map2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

public class Map1 extends Mapper<LongWritable,Text,    Text,IntWritable> {
    static int count=1;

    //添加后缀：  使得reducer平均分布数据
    int after=0;
    Random r=new Random();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        after=context.getNumReduceTasks();//reducer个数
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //拆分单词，    单词数：  1
        String[] ss = value.toString().split(" ");

        for(String st: ss){//  //添加后缀：  使得reducer平均分布数据
            context.write(new Text(st+"_"+r.nextInt(after)),   new IntWritable(1));


            System.out.print("mapper...遍历string: "+st+", \t");
        }
        System.out.println("mapper......"+(count++)+"..........");
    }
}
