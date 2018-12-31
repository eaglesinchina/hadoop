package mr_sort.sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //job初始化
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS","file:///");
        Job job = Job.getInstance(conf);
        job.setJarByClass(App.class);

        //输入
        job.setInputFormatClass(KeyValueTextInputFormat.class);//输入格式：  txt, txt
        //设置全排序采样类TotalOrderPartitioner
        job.setPartitionerClass(TotalOrderPartitioner.class);

        //最小切片size
       // FileInputFormat.setMinInputSplitSize(job,500);// 2k
        FileInputFormat.addInputPath(job,new Path("/home/centos/a.txt"));

        //map, reduce
        job.setMapperClass(Map1.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setNumReduceTasks(4);
        job.setReducerClass(Red1.class);


        //输出
        Path path = new Path("/home/centos/txt/mr_sort-out");
        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(path)) fs.delete(path,true);

        FileOutputFormat.setOutputPath(job,path);


        /**
         * 随机采样，比较浪费性能，耗费资源
         * @param freq 每个key被选择的概率 ，大于采样数(2) / 所有key数量(n)
         * @param numSamples 所有切片中需要选择的key数量
         */
        //设置采样器类型
        InputSampler.RandomSampler<Text,Text> sampler = new InputSampler.RandomSampler<Text,Text>(0.01,10);
        //设置采样数据地址
        TotalOrderPartitioner.setPartitionFile(job.getConfiguration(),new Path("/home/centos/txt/samp-out.txt"));
        //写入采样数据
        InputSampler.writePartitionFile(job,sampler);


        //等待工作完成
        boolean flag = job.waitForCompletion(true);

        if(flag){//二次数据整理：  new  job2
            Job job2 = Job.getInstance(conf);
            job2.setJarByClass(App.class);
            job2.setJobName("job2");

            //map, reduce
            job2.setMapperClass(Map2.class);
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(IntWritable.class);

            job2.setReducerClass(Red2.class);

            //输入路径， 输出路径
            job2.setInputFormatClass(KeyValueTextInputFormat.class);//输入格式：  txt, txt
            FileInputFormat.addInputPath(job2,new Path("/home/centos/txt/mr_sort-out"));
            Path path2 = new Path("/home/centos/txt/mr_sort-final");
            if(fs.exists(path2)) fs.delete(path2,true);
            FileOutputFormat.setOutputPath(job2, path2);

            //提交作业
            boolean flag2 = job2.waitForCompletion(true);
            if(flag2){//三次作业完成：  再次排序
                Job job3 = Job.getInstance(conf);
                job3.setJarByClass(App.class);

                //map, reduce
                job3.setMapperClass(Map3.class);
                job3.setMapOutputKeyClass(Passwd.class);
                job3.setMapOutputValueClass(NullWritable.class);

                //输入输出
                job3.setInputFormatClass(KeyValueTextInputFormat.class);//输入格式：  txt, txt
                FileInputFormat.addInputPath(job3,new Path("/home/centos/txt/mr_sort-final"));
                FileOutputFormat.setOutputPath(job3,new Path("/home/centos/out-final"));

                job3.waitForCompletion(true);


            }

        }

    }
}
