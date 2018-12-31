package mr_sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //job初始化
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);
        job.setJarByClass(App.class);

        //输入
        FileInputFormat.addInputPath(job,new Path("/home/centos/1.txt"));


        //map, reduce
        job.setMapperClass(Map1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(Red1.class);


        //输出
        FileOutputFormat.setOutputPath(job,new Path("/home/centos/txt/t1/a-mpr-out2"));
        //等待工作完成
        job.waitForCompletion(true);
    }
}
