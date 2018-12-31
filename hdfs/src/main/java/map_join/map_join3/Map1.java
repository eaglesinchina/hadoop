package map_join.map_join3;
//数据加工：  取出元数据，  加工---》输出到reduce计算

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable,Text, ComparKey1,Text> {

    String fname =null;


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //判断是否： ord, cus
        InputSplit inputSplit = context.getInputSplit();
        FileSplit f = (FileSplit) inputSplit;

        fname = f.getPath().getName();

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        if(fname.contains("custom")){//1	tom	20
            new ComparKey1();

        }else{//1	no001	12.3	7

        }


    }
}
