package custom_image2;

import custom_image2.util.ReadAppUtil;
import custom_image2.util.UserImgUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;

public class Map1 extends Mapper<LongWritable, Text,    Text,IntWritable> {



    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //统计用户信息：  （用户id_appId,  使用时长）

        String[] info = value.toString().split("\\|");
        String userId=info[UserImgUtil.user_id];
        String appid=info[UserImgUtil.user_appid];
        int dureTime= Integer.parseInt(info[UserImgUtil.user_duration]);


//        String time=info[11];

        if(userId!=null && appid!=null && !appid.equals(" ") && !appid.equals("") && !userId.equals("")) {

            int appid2=Integer.parseInt(appid);
            context.write(new Text(userId+"|"+appid2), new IntWritable(dureTime));
        }
    }
}
