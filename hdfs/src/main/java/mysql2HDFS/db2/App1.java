package mysql2HDFS.db2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

public class App1 {

    public static void main(String[] args) throws Exception {
        //job

        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","file:///");
        conf.set("mapreduce.app-submission.cross-platform", "true");
//        conf.set("mapreduce.job.jar", "/home/centos/wordcount.jar");


        Job job = Job.getInstance(conf);
        //job属性
        job.setJarByClass(App1.class);
        job.setJobName("mysql2HDFS");

        //map, red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(DbBean2.class);
        job.setOutputValueClass(NullWritable.class);

        //输入输出
        job.setInputFormatClass(DBInputFormat.class);
        DBConfiguration.configureDB(job.getConfiguration(),"com.mysql.jdbc.Driver",
                "jdbc:mysql://beer:3306/mysql2HDFS","root","daitoue");
        DBInputFormat.setInput(job,DbBean.class,"select * from per",
                "select count(*) from per");
        DBOutputFormat.setOutput(job,"wc",3);

        job.waitForCompletion(true);

    }

}
