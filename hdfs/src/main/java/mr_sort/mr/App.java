package hadoop.comm.mpr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(App.class);
        job.setJobName("max word");
        //map, red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //输入输出
//        job.setInputFormatClass(TextInputFormat.class);
//        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        FileInputFormat.addInputPath(job,new Path("/word.txt"));
        FileOutputFormat.setOutputPath(job,new Path("/max-wrod-count699"));

        //提价
        boolean b = job.waitForCompletion(true);

        if(b){
            Job job2 = Job.getInstance(conf);
            job2.setJarByClass(App.class);
            job2.setJobName("max word2");
            //map, red
            job2.setMapperClass(Map2.class);
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(IntWritable.class);

            FileInputFormat.addInputPath(job2,new Path("/max-wrod-count699"));
            FileOutputFormat.setOutputPath(job2,new Path("/max-wrod-count68-res"));
            job2.waitForCompletion(true);
            System.out.println("over");
        }


    }
}
