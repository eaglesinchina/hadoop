package mr_sort.map3;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.Random;


public class Partion1 extends Partitioner<Text,IntWritable> {
    private int redCount;
    //随机数产生器
    Random r=new Random();

    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        System.out.println("partion===============-----"+i);
        return r.nextInt(i);

    }

    //get,set
    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }
}
