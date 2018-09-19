package mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WcApp {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //创建job
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);
        job.setJobName("wc");
        job.setJarByClass(WcApp.class);


        //配置map,red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //输入输出
//        job.setInputFormatClass(FileInputFormat.class);
//        job.setOutputFormatClass(FileOutputFormat.class);

        FileInputFormat.addInputPath(job,new Path("/home/centos/wc.txt"));
        FileOutputFormat.setOutputPath(job,new Path("/home/centos/wc-out"));

        job.waitForCompletion(true);
    }

}
