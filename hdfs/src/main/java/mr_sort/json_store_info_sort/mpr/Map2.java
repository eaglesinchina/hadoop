package mr_sort.json_store_info_sort.mpr;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 75144086_效果赞 1,
 *
 * ||   job2
 * ||
 * \/
 *
 * 75144086	效果赞_2"
 */

public class Map2 extends Mapper<LongWritable,Text,  Text, Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //读取：   评论string
        String[] info = value.toString().split("\t");

        String[] msgs = info[0].split("_");
        String sum = info[1];

        //拼接: 键值对---》次数
            //店铺id +  评论内容----》 1次
            context.write(new Text(msgs[0]),  new Text(msgs[1]+"_"+sum));

        }

    }

