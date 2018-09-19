package callog.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //job
        Configuration conf = HBaseConfiguration.create();
        conf.set(TableInputFormat.INPUT_TABLE,"weibo:wc-words");
        conf.set(TableOutputFormat.OUTPUT_TABLE,"weibo:wc");
        Job job = Job.getInstance(conf);

        job.setJarByClass(App.class);
        job.setJobName("wc-table");

        //map,red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Put.class);

        //输入输出
        job.setInputFormatClass(TableInputFormat.class);
        job.setOutputFormatClass(TableOutputFormat.class);

        job.waitForCompletion(true);


    }
}
