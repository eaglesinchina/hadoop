package custom_image;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map1 extends Mapper<LongWritable, Text,    Text,IntWritable> {

    //userData  split("\\|")
    //			  id = arr[0];
    //			  appid = arr[15];
    //			  time = arr[11];
    //			  duration = arr[12];
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //统计用户信息：  （用户id_appId,  使用时长）

        String[] info = value.toString().split("\\|");
        String userId=info[0];
        String appid=info[15];
        int dureTime= Integer.parseInt(info[12]);


//        String time=info[11];

        if(userId!=null && appid!=null && !appid.equals(" ") && !appid.equals("") && !userId.equals("")) {

            int appid2=Integer.parseInt(info[15]);
            context.write(new Text(userId+"|"+appid2+"|"), new IntWritable(dureTime));
        }
    }
}
