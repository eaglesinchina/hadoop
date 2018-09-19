package mpr.secondSort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws Exception {
        //创建任务
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);


        //添加属性
        job.setMapOutputKeyClass(Map1.class);
        job.setReducerClass(Red1.class);
        job.setJarByClass(App.class);

        //输入
        FileInputFormat.addInputPath(job,new Path("/home/centos/txt/kv.txt"));


        //map, reduce
        job.setMapperClass(Map1.class);
        job.setMapOutputKeyClass(TmpBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setReducerClass(Red1.class);
        job.setNumReduceTasks(2);//多个reducer, 使得有空任务）


        //输出
        Path path = new Path("/home/centos/sort-tmp");
        FileSystem fs = FileSystem.get(conf);

        if(fs.exists(path)) fs.delete(path,true);

        FileOutputFormat.setOutputPath(job,path);
        //等待工作完成
         job.waitForCompletion(true);



    }

}
