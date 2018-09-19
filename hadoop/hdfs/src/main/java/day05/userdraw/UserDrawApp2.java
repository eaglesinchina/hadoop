package day05.userdraw;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class UserDrawApp2 {

    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "file:///");

        FileSystem fs = FileSystem.get(conf);

        //通过配置文件初始化job
        Job job = Job.getInstance(conf);

        //设置job名称
        job.setJobName("userdraw");

        //job入口函数类
        job.setJarByClass(UserDrawApp2.class);

        //设置mapper类
        job.setMapperClass(UserDrawMapper2.class);

        //设置reducer类
        job.setReducerClass(UserDrawReducer2.class);

        //设置map的输出k-v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(CompKey.class);

        //设置reduce的输出k-v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        Path pin = new Path("/home/centos/qq-files/custom_img/userdata.txt");
        Path pout = new Path("/home/centos/txt/user-img/userimg-red1");

        MultipleOutputs.addNamedOutput(job,"mos",TextOutputFormat.class,Text.class,Text.class);

        //设置输入路径
        FileInputFormat.addInputPath(job, pin);


        //设置输出路径
        FileOutputFormat.setOutputPath(job, pout);

        if (fs.exists(pout)) {
            fs.delete(pout, true);
        }

        job.setNumReduceTasks(1);

        //执行job
        Boolean b = job.waitForCompletion(true);
    }

}
