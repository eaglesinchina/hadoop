package userdraw.custom_image3;

import userdraw.custom_image3.util.Double2pointUtil;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;

public class Red2 extends Reducer<Text, Text, Text, NullWritable> {


    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //+sYOjvE5nreZCR0ZHEBXuA==      0.16|0.16|   0.0|32.0|48.0|32.0|48.0   列求和

        //21812|Æ·ÊéÍø|0.001|0.001|0.1|0.3|0.3|0.2|0.1
        //271813|Æ·ÖÊ365|0.4|0.6|0|0.1|0.3|0.3|0.3

        double manTime = 0;
        double womanTime = 0;

        double age1Time = 0;
        double age2Time = 0;
        double age3Time = 0;
        double age4Time = 0;
        double age5Time = 0;

        String userid = key.toString();

        //多少个app
        ArrayList containor = new ArrayList();
        for (Text value : values) {
            String[] info  = value.toString().split("\\|");
            //存入数据
            containor.add(info);
        }

        //列： 求和
        for (int i = 0; i < containor.size(); i++) {

            String[] info = (String []) containor.get(i);

            manTime += Double.parseDouble(info[0]);
            womanTime +=Double.parseDouble(info[1]);
            age1Time += Double.parseDouble(info[2]);

            age2Time += Double.parseDouble(info[3]);
            age3Time +=Double.parseDouble(info[4]);
            age4Time += Double.parseDouble(info[5]);
            age5Time += Double.parseDouble(info[6]);
        }

        double totalAgeTime = age1Time + age2Time + age3Time + age4Time + age5Time;
        double ratMan =manTime / (manTime + womanTime);
        double ratWoman = womanTime / ((manTime + womanTime));

        double ratAge1 =age1Time / totalAgeTime;
        double ratAge2 =age2Time / totalAgeTime;
        double ratAge3 =age3Time / totalAgeTime;
        double ratAge4 =age4Time / totalAgeTime;
        double ratAge5 =age5Time / totalAgeTime;
       // 写出数据
        context.write(new Text(userid + ",  "
                        + Double2pointUtil.get2DoubleStr(ratMan) + "|"
                        + Double2pointUtil.get2DoubleStr(ratWoman) +" |"
                        + Double2pointUtil.get2DoubleStr(ratAge1 )+ "|"
                        + Double2pointUtil.get2DoubleStr(ratAge2 )+ "|"
                        + Double2pointUtil.get2DoubleStr(ratAge3) + "|"
                        + Double2pointUtil.get2DoubleStr(ratAge4 )+ "|"
                        + Double2pointUtil.get2DoubleStr(ratAge5)),
                NullWritable.get());
    }

}
