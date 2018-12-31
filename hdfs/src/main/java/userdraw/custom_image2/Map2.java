package userdraw.custom_image2;

import userdraw.custom_image2.util.ReadAppUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Map;

public class Map2 extends Mapper<LongWritable, Text,    Text,NullWritable> {

    //mutipleOutputFormat:   输出记录

    Map<String, String> appMap=null;


    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //加载app数据：  拼合为一体
        try {
            appMap = ReadAppUtil.getAppMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringBuilder sb=new StringBuilder();
        // uid|appid  --->使用时间汇总

        //----+ app数据（map.get(id))
        //        //        //+/+heh935ZGUuIDEm/tR4Q==|10001|70|
        //        //                    //10001 |QQ|0.001|0.001|    0|0.2|0.3|0.2|0.3
                //appid                                             4  5  6   7   8
        String[] userInfo = value.toString().split("\\|");

        //        //该用户话费：总时间
        String userid=userInfo[0];
        String appid = userInfo[1];
        String appInfo=appMap.get(appid);
        int totalTime=Integer.parseInt(userInfo[2]);

        //取出app信息
        String[] appInfoArr = appInfo.split("\\|");
        //        //男时间： 女时间
        Double manTime=totalTime* Double.parseDouble(appInfoArr[2]);
        Double womanTime=totalTime* Double.parseDouble(appInfoArr[3]);
        //
        //
        //        //年龄1 时间  |   年龄2时间  |  年龄3 时间 |  年龄4时间  |  年龄5 时间，

        double age1Time=totalTime* Double.parseDouble(appInfoArr[4]);
        double age2Time=totalTime* Double.parseDouble(appInfoArr[5]);
        double age3Time=totalTime* Double.parseDouble(appInfoArr[6]);
        double age4Time=totalTime* Double.parseDouble(appInfoArr[7]);
        double age5Time=totalTime* Double.parseDouble(appInfoArr[8]);

//        String time=info[11];

      //输出数据： userid + 男女时间   年龄1 时间  |   年龄2时间  |  年龄3 时间 |  年龄4时间  |  年龄5 时间，
        String txt=sb.append(userid+"|")
                .append(manTime+"|")
                .append(womanTime+"|")
                .append(age1Time+"|")
                .append(age2Time+"|")
                .append(age3Time+"|")
                .append(age4Time+"|")
                .append(age5Time).toString();

        context.write(new Text(txt), NullWritable.get());
    }


}
