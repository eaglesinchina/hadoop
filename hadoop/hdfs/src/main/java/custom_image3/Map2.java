package custom_image3;

import custom_image3.util.ReadAppUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.util.Map;

public class Map2 extends Mapper<LongWritable, Text, Text, Text> {

    MultipleOutputs format;//  输出记录

    Map<String, String> appMap = null;
    Map<String, String> userDataMap = null;
    Path path = new Path("/home/centos/txt/user-img/multi-out-m-00000");

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        format = new MultipleOutputs(context);

        //加载app数据：  拼合为一体
        try {
            appMap = ReadAppUtil.getAppMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            userDataMap = ReadAppUtil.getUserAppDataMap();
        }catch (Exception e){
            System.out.println("userDataMap....error..");
            e.printStackTrace();
        }

        //删除原文件，保存新文件： 累加数据
        FileSystem fs = FileSystem.get(context.getConfiguration());
        if (fs.exists(path))
            fs.delete(path, true);
    }


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        // uid|appid  --->使用时间汇总
        //----+ app数据（map.get(id))
        //        //                    //10001 |QQ|0.001|0.001|    0|0.2|0.3|0.2|0.3
        //appid                                             4  5  6   7   8
        String[] userInfo = value.toString().split("\\|");

        //        //该用户话费：总时间
        String userid = userInfo[0];
        String appid = userInfo[1];
        int totalTime = Integer.parseInt(userInfo[2]);
        if (totalTime == 0) return;

        //取出app信息
        String appInfo = appMap.get(appid);
        String[] appInfoArr = appInfo.split("\\|");
        //        //男时间： 女时间
        Double manTime = totalTime * Double.parseDouble(appInfoArr[2]);
        Double womanTime = totalTime * Double.parseDouble(appInfoArr[3]);
        //        //年龄1 时间  |   年龄2时间  |  年龄3 时间 |  年龄4时间  |  年龄5 时间，
        double age1Time = totalTime * Double.parseDouble(appInfoArr[4]);
        double age2Time = totalTime * Double.parseDouble(appInfoArr[5]);
        double age3Time = totalTime * Double.parseDouble(appInfoArr[6]);
        double age4Time = totalTime * Double.parseDouble(appInfoArr[7]);
        double age5Time = totalTime * Double.parseDouble(appInfoArr[8]);

        //输出数据： userid + 男女时间   年龄1 时间  |   年龄2时间  |  年龄3 时间 |  年龄4时间  |  年龄5 时间，
        String txt = sb.append(manTime + "|")
                .append(womanTime + "|")
                .append(age1Time + "|")
                .append(age2Time + "|")
                .append(age3Time + "|")
                .append(age4Time + "|")
                .append(age5Time).toString();

        context.write(new Text(userid), new Text(txt));


        //取出原来的数据+ 新的数据
        if (userDataMap == null) {
            format.write("text", new Text(userid), new Text(txt), "/home/centos/txt/user-img/multi-out");
        } else {
            String[] oldData = userDataMap.get(userid).split("\\|");
            manTime += Double.parseDouble(oldData[0]);
            womanTime += Double.parseDouble(oldData[1]);
            age1Time += Double.parseDouble(oldData[2]);
            age2Time += Double.parseDouble(oldData[3]);
            age3Time += Double.parseDouble(oldData[4]);
            age4Time += Double.parseDouble(oldData[5]);
            age5Time += Double.parseDouble(oldData[6]);

            sb.delete(0, sb.length());
            String txt2 = sb.append(manTime + "|")
                    .append(womanTime + "|")
                    .append(age1Time + "|")
                    .append(age2Time + "|")
                    .append(age3Time + "|")
                    .append(age4Time + "|")
                    .append(age5Time).toString();
            format.write("text", new Text(userid), new Text(txt2), "/home/centos/txt/user-img/multi-out");
        }
    }
}
