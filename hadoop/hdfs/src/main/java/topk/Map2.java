package topk;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

public class Map2 extends Mapper<LongWritable, Text, IntWritable, Text> {

    //输入值(count, 温度值）
    TreeSet<CompareKey> set=new TreeSet<CompareKey>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取： top数值


    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] msg = value.toString().split("\t");
        set.add(new CompareKey(Integer.parseInt(msg[0]),  msg[1]));

        //取10个最大值
        if(set.size()>10){
            set.remove(set.last());//大的在前
        }

    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        int count=1;
        for(CompareKey key: set){

            context.write(new IntWritable(key.getSum()), new Text(key.getTmp()));

            if((count++)%10==0)
            context.write(new IntWritable(0), new Text("-----------\n"));
        }
    }
}
