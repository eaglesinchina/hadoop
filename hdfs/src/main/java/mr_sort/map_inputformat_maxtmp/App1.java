package mr_sort.map_inputformat_maxtmp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class App1 {

    public static void main(String[] args) throws Exception {
        //seqfil
       // MkSeq.mkSeq();

        //job

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);
        //job属性
        job.setJarByClass(App1.class);
        job.setJobName("tmp");

        //map, red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setInputFormatClass(SequenceFileInputFormat.class);
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //输入输出
        FileInputFormat.addInputPath(job,new Path("/home/centos/txt/tmp-out-seq"));
        Path path = new Path("/home/centos/txt/tmp-seq-out");
        FileSystem fs = FileSystem.get(conf);

        if(fs.exists(path)) fs.delete(path,true);

        FileOutputFormat.setOutputPath(job,path);

        job.waitForCompletion(true);

    }

}
