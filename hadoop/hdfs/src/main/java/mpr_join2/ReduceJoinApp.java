package mpr_join2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class ReduceJoinApp {

    //
    public static void main(String[] args) throws Exception {
        //job
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);
        job.setJarByClass(ReduceJoinApp.class);
        job.setJobName("ord-custom");

        //map,red
        job.setMapperClass(ReduceJoinMapper.class);
        job.setReducerClass(ReduceJoinReducer.class);
        job.setMapOutputKeyClass(CompKey.class);
        //比较器
        job.setGroupingComparatorClass(GroupComparator.class);
        job.setMapOutputValueClass(Text.class);


        //输入输出
        FileInputFormat.addInputPath(job,new Path("/home/centos/cus-ord"));

        FileOutputFormat.setOutputPath(job,new Path("/home/centos/cus-ord-out"));


        //
        job.waitForCompletion(true);



    }
}
