package mr_sort.json_store_info_sort.mpr2;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 75144086_效果赞   12,
 *                  ===>75144086,   {效果赞   1}
 *
 */

public class Map2 extends Mapper<LongWritable,Text, Text, ComparKey> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //读取：   评论string
        String[] info = value.toString().split("\t");

        String storid = info[0].split("_")[0];//75144086  _  效果赞
        String comment=info[0].split("_")[1];


        String sum = info[1];//1

        //拼接: 键值对---》次数
            //店铺id +  评论内容----》 1次
            context.write(new Text(storid), new ComparKey(Integer.parseInt(sum), comment));

        }

    }

