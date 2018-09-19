package custom_image3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

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
        Path p = new Path("/home/centos/txt/user-img/userimg-red1");
        if(fs.exists(p))
            fs.delete(p, true);
        FileOutputFormat.setOutputPath(job,p);


        //提交
        boolean b=job.waitForCompletion(true);
        if(b){
            Job job2 = Job.getInstance(conf);
            job2.setJarByClass(App1.class);
            job2.setJobName("custom_app_info2");

            //map,red
            job2.setMapperClass(Map2.class);
            job2.setReducerClass(Red2.class);
            //多重输出格式
            MultipleOutputs.addNamedOutput(job2,"text",TextOutputFormat.class,Text.class,Text.class);
            MultipleOutputs.addNamedOutput(job2,"seq",SequenceFileOutputFormat.class,Text.class,IntWritable.class);

            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(Text.class);
            job2.setOutputKeyClass(Text.class);
            job2.setOutputValueClass(NullWritable.class);

            //输入输出
            FileInputFormat.addInputPath(job2,new Path("/home/centos/txt/user-img/userimg-red1"));
            Path p2 = new Path("/home/centos/txt/user-img/userimg-red2");

            if(fs.exists(p2))
                fs.delete(p2, true);
            FileOutputFormat.setOutputPath(job2,p2);


            //提交
            job2.waitForCompletion(true);

        }
    }
}
