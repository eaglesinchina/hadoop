package json.mpr2;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

/**
 * 75144086	效果赞","无推销","无办卡","服务热情","干净
 *
 * 75144086_效果赞 1,   75144086_无推销 23 , 75144086_无办卡 43:          初次job
 */
public class Map1 extends Mapper<LongWritable,Text,  Text, IntWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //读取：   评论string
        String[] info = value.toString().split("\t");

        String bossid = info[0];
        List<String> msgs=null;
        try {
            msgs = JsonUtils.getComment(info[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //拼接: 键值对---》次数
        for (String msg : msgs) {
            //店铺id +  评论内容----》 1次
            context.write(new Text(bossid+"_"+msg),   new IntWritable(1));

        }

    }
}
