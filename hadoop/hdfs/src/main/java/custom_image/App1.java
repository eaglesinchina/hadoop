package custom_image;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class App1 {

    public static void main(String[] args) throws Exception {
        //job
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs = FileSystem.get(conf);

        Job job = Job.getInstance(conf);
        job.setJarByClass(App1.class);
        job.setJobName("custom_app_info");

        //map,red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //输入输出
        FileInputFormat.addInputPath(job,new Path("/home/centos/qq-files/custom_img/userdata.txt"));
        Path p = new Path("/home/centos/txt/user-img-out");
        if(fs.exists(p))
            fs.delete(p, true);
        FileOutputFormat.setOutputPath(job,p);

        //提交
        job.waitForCompletion(true);



    }
}
