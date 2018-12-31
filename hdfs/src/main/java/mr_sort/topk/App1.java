package mr_sort.topk;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class App1 {
    public static void main(String[] args) throws Exception {
        // txt/kv:   取  温度频率最高的（mr_sort.top10  ）

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        FileSystem fs= FileSystem.get(conf);


        //job
        Job job = Job.getInstance(conf);
        job.setJarByClass(App1.class);
        job.setJobName("mr_sort/topk");

        //map,red
        job.setMapperClass(Map1.class);
        FileInputFormat.setMaxInputSplitSize(job,50*1024 );//最大切片
        job.setNumReduceTasks(3);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);


        //输入输出
        FileInputFormat.addInputPath(job,new Path("/home/centos/qq-files/src-data/kv.txt"));

        //定义两种输出格式
        MultipleOutputs.addNamedOutput(job,"text",TextOutputFormat.class,Text.class,IntWritable.class);
        MultipleOutputs.addNamedOutput(job,"seq",SequenceFileOutputFormat.class,Text.class,IntWritable.class);

        Path path = new Path("/home/centos/txt/top-k-out");
        if(fs.exists(path)){
            fs.delete(path,true);
        }
        FileOutputFormat.setOutputPath(job,path);

        //启动
        boolean flag = job.waitForCompletion(true);
        if(flag){
            //job
            Job job2 = Job.getInstance(conf);
            job2.setJarByClass(App1.class);
            job2.setJobName("mr_sort/topk2");

            //map,red
            job2.setMapperClass(Map2.class);

            job2.setMapOutputKeyClass(IntWritable.class);
            job2.setMapOutputValueClass(Text.class);


            //输入输出
            FileInputFormat.addInputPath(job2,new Path("/home/centos/txt/top-k-out"));
            Path path2 = new Path("/home/centos/txt/top-k-out2");
            if(fs.exists(path2)){
                fs.delete(path2,true);
            }
            FileOutputFormat.setOutputPath(job2,path2);

            //启动
            job2.waitForCompletion(true);


        }


    }

}
