package mr_sort.json_store_info_sort.mpr2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App1 {

    public static void main(String[] args) throws Exception {
        //job
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");


        Job job = Job.getInstance(conf);
        //配置job
        job.setJarByClass(App1.class);
        job.setJobName("get-sum-comment");

        //map, red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //输入输出
        FileInputFormat.addInputPath(job,new Path("/home/centos/txt/log.txt"));
        Path path = new Path("/home/centos/txt/out-comment-sum");
        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(path))  fs.delete(path, true);

        FileOutputFormat.setOutputPath(job,path);


        //提交job
        boolean flag = job.waitForCompletion(true);
        /**
         * 78824187_干净卫生	7
         * 78824187_性价比高	8
         */

        if(flag){

            Job job2 = Job.getInstance(conf);
            //配置job
            job2.setJarByClass(App1.class);
            job2.setJobName("get-sum-comment2");

            //map, red
            job2.setMapperClass(Map2.class);
            job2.setReducerClass(Red2.class);

            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(ComparKey.class);

            //输入输出
            FileInputFormat.addInputPath(job2,new Path("/home/centos/txt/out-comment-sum"));
            Path path2 = new Path("/home/centos/txt/out-comment-sum2");
            if(fs.exists(path2))  fs.delete(path2, true);
            FileOutputFormat.setOutputPath(job2,path2);


            //提交job
             job2.waitForCompletion(true);
        }
    }
}
