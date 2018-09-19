package map_join;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class App  {

    public static void main(String[] args) throws Exception {
        //job
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");


        Job job = Job.getInstance(conf);
        job.setJarByClass(App.class);

        //map
        job.setMapperClass(JoinMap.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        //输入输出
        FileInputFormat.addInputPath(job, new Path("/home/centos/txt/orders.txt"));
        FileOutputFormat.setOutputPath(job,new Path("/home/centos/map-join"));

        job.waitForCompletion(true);

    }


}
