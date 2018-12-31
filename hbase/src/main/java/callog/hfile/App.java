package callog.hfile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.HFileOutputFormat2;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //创建job
        Configuration conf = HBaseConfiguration.create();
        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);

        job.setJarByClass(App.class);
        job.setJobName("tab-wc-hfile");

        //map-red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(ImmutableBytesWritable.class);
        job.setOutputValueClass(Cell.class);

        //输入输出
        job.setOutputFormatClass(HFileOutputFormat2.class);
        HFileOutputFormat2.setOutputPath(job,new Path("/home/centos/hfile.txt"));
//        HFileOutputFormat2.configureIncrementalLoad(job, table,conn.getRegionLocator(table.getName()));
        FileInputFormat.addInputPath(job,new Path("/wc.txt"));

        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("weibo:wc"));



        //提交job
        job.waitForCompletion(true);
    }
}
