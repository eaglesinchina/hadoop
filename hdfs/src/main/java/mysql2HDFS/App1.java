package mysql2HDFS;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class App1 {

    public static void main(String[] args) throws Exception {
        //job

        Configuration conf = new Configuration();
        conf.set("","");
//        conf.set("fs.defaultFS","file:///");

        Job job = Job.getInstance(conf);
        //job属性
        job.setJarByClass(App1.class);
        job.setJobName("mysql2HDFS");

        //map, red
        job.setMapperClass(Map1.class);
        job.setReducerClass(Red1.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //输入输出
        job.setInputFormatClass(DBInputFormat.class);
        DBConfiguration.configureDB(job.getConfiguration(),"com.mysql.jdbc.Driver",
                "jdbc:mysql://beer:3306/mysql2HDFS","root","daitoue");

        DBInputFormat.setInput(job,DbBean.class,"select * from per","select count(*) from per");
        Path path = new Path("/home/centos/txt/tmp-mysql2HDFS-out");
        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(path)) fs.delete(path,true);
        FileOutputFormat.setOutputPath(job,path);

        job.waitForCompletion(true);

    }

}
