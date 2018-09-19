package day05.userdraw;

import day05.userdraw.util.ConfUtil;
import day05.userdraw.util.ReadAppTab;
import day05.userdraw.util.ReadMOSFile;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * +/rmMLtMV+s+gXTDoOaoxQ==|10005|824
 * +/rmMLtMV+s+gXTDoOaoxQ==|220499|98
 * +/rmMLtMV+s+gXTDoOaoxQ==|70093|75610
 * <p>
 * AppTab
 * 10005|微信|0.001|0.001|0|0.2|0.3|0.2|0.3
 * 220499|搜狐浏览器|0.001|0.001|0.001|0.002|0.002|0.002|0.003
 * 70093|豌豆荚|0.6|0.4|0.001|0.002|0.002|0.002|0.003
 * <p>
 * 男|女|年龄1|年龄2|年龄3|年龄4|年龄5
 * <p>
 * 824x0.001 | 824x0.001 | 824x0 | 824x0.2 | 824x0.3 | 824x0.2 |824x0.3
 * 98x0.001 | 98x0.001 |98x0.001 |98x0.002 |98x0.002 |98x0.002 |98x0.003
 * 75610x0.6 | 75610x0.4 | xxxxxx
 * <p>
 * sum1    | sum2  | sum3 | .....  => mos
 * <p>
 * 50000  |  30000 | xxxxxx ...
 * <p>
 * 0.625 | 0.375
 * <p>
 * <p>
 * 可以把10005|824 => MapWritable<Text,IntWritable>
 * <p>
 * appTab<10005,10005|微信|0.001|0.001|0|0.2|0.3|0.2|0.3>
 * arr[7] = {0,0,0,0,0,0,0}
 * for(){
 * MapWritable<10005, 824>
 * getKey()
 * appTab.get<10005> => 10005|微信|0.001|0.001|0|0.2|0.3|0.2|0.3
 * <p>
 * arr[0] += 0.001 x 824
 * arr[1] +=
 * ......
 * }
 * <p>
 * mos.write(phone, arr1|arr2|arr3|arr4|arr5|arr6|arr7)
 * <p>
 * <p>
 * id|arr1/(arr1+arr2) | arr2/(arr1+arr2) | .......
 */
public class UserDrawReducer2 extends Reducer<Text, CompKey, Text, Text> {
    Map<String, String> tabMap;
    MultipleOutputs mos;
    ConfUtil confUtil;
    Map<String, String[]> mosMap;




    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        tabMap = ReadAppTab.readFile();
        mos = new MultipleOutputs(context);
        confUtil = new ConfUtil();
        mosMap = ReadMOSFile.readMOS();
    }

    @Override
    protected void reduce(Text key, Iterable<CompKey> values, Context context) throws IOException, InterruptedException {
        float male;
        float female;
        float age1;
        float age2;
        float age3;
        float age4;
        float age5;

        if(mosMap.size() == 0 || mosMap.get(key.toString()) == null){
            male = 0;
            female = 0;
            age1 = 0;
            age2 = 0;
            age3 = 0;
            age4 = 0;
            age5 = 0;

        }
        else {
            male = Float.parseFloat(mosMap.get(key.toString())[0]);
            female = Float.parseFloat(mosMap.get(key.toString())[1]);
            age1 = Float.parseFloat(mosMap.get(key.toString())[2]);
            age2 = Float.parseFloat(mosMap.get(key.toString())[3]);
            age3 = Float.parseFloat(mosMap.get(key.toString())[4]);
            age4 = Float.parseFloat(mosMap.get(key.toString())[5]);
            age5 =Float.parseFloat(mosMap.get(key.toString())[6]);

        }


        for (CompKey value : values) {
            String appid = value.getAppid();
            int duration = value.getDuration();

            String line = tabMap.get(appid);

            String[] arr = line.split("\\|");

            male += Float.parseFloat(arr[2]) * duration;
            female += Float.parseFloat(arr[3]) * duration;
            age1 += Float.parseFloat(arr[4]) * duration;
            age2 += Float.parseFloat(arr[5]) * duration;
            age3 += Float.parseFloat(arr[6]) * duration;
            age4 += Float.parseFloat(arr[7]) * duration;
            age5 += Float.parseFloat(arr[8]) * duration;
        }

        String newVal = male + "|" + female + "|" + age1 + "|" + age2 + "|" + age3 + "|" + age4 + "|" + age5;

        mos.write("mos",key,new Text(newVal), confUtil.mos);

        float male_weight = formatFloat(male / (male + female));
        float female_weight = formatFloat(female / (male + female));
        float age1_weight = formatFloat(age1 / (age1 + age2 + age3+ age4 + age5));
        float age2_weight = formatFloat(age2 / (age1 + age2 + age3+ age4 + age5));
        float age3_weight = formatFloat(age3 / (age1 + age2 + age3+ age4 + age5));
        float age4_weight = formatFloat(age4 / (age1 + age2 + age3+ age4 + age5));
        float age5_weight = formatFloat(age5 / (age1 + age2 + age3+ age4 + age5));

        context.write(null, new Text(key.toString() + "|" + male_weight + "|" + female_weight + "|" + age1_weight+"|"+ age2_weight+"|"+ age3_weight+"|"+ age4_weight+"|"+ age5_weight));


    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        mos.close();
    }

    public static float formatFloat(float weight){
        try {
            BigDecimal b = new BigDecimal(weight);
            float f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
            return f1;

        } catch (Exception e) {
        }
        return 0;

    }




}
