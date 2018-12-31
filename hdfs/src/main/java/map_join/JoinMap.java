package map_join;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JoinMap extends Mapper<LongWritable,Text, Text,NullWritable> {

    Map map=new HashMap<Integer,String>();

    //读取文件： customer
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //流.read(): 文件
        BufferedReader br = new BufferedReader(new FileReader("/home/centos/txt/customer.txt"));
        String line=null;
        while((line=br.readLine())!=null){
            map.put(line.split("\t")[0],line);
        }

        //
        br.close();
    }


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
       //8	tom8	9

        //读取： order文件
        String[] ord = value.toString().split("\t"); //8	no008	6.0	1
        String content = (String)map.get(ord[3]);

        String[] msg = content.split("\t");
        String uid = msg[0];
        String uname = msg[1];

        String info = uid+"\t"+uname + "\t" + ord[1] + "\t" + ord[2];

        context.write(new Text(info),NullWritable.get());


    }
}
