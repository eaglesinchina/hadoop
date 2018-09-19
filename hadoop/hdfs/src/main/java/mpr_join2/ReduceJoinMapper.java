package mpr_join2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class ReduceJoinMapper extends Mapper<LongWritable,Text,CompKey,Text> {

    String fileName;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {

        //通过切片得到文件名
        FileSplit split = (FileSplit) context.getInputSplit();
        fileName = split.getPath().getName();

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        if(fileName.contains("customers")){
            int flag = 1;
            String cusLine = value.toString();
            String[] arr = cusLine.split("\t");
            if(arr.length == 3){//1	tom	20
                String id = arr[0];
                CompKey ck = new CompKey(flag,id);
                context.write(ck,new Text(cusLine));
            }


        }
        else {
            int flag = 2;

            String ordLine = value.toString();
            String[] msg = ordLine.split("\t");

            //写出: compKey, 数据
            context.write(new CompKey(flag,  msg[3]) ,value);

        }


    }
}
