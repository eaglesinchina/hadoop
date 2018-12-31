package udtf.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Description(
        name = "json util",
        value = "parse json-->array/list",
        extended = "...."
)
/**
 * 532868065.518   #192.168.23.1   #1532868067548#  200
 * #{
 * \"appEventLogs\":[{\"createdAtMs\":1530412800000,\"eventId\":\"share\",\"logType\":\"event\",\"mark\":\"4\",\"musicID\":\"傲红尘\"}],
 * \"appPageLogs\":[{\"createdAtMs\":1530449520000,\"logType\":\"page\",\"nextPage\":\"list.html\",\"pageId\":\"list.html\",\"pageViewCntInSession\":0,\"visitIndex\":\"2\"}],
 deviceId
 */
public class MyJsonUtil2 extends UDF {


    public static List<Object[]> evaluate(String line) {//输入json数据{....}
        //输入的处理
        String s = line.replaceAll("\\\\", "");

        ArrayList<Object[]> list = new ArrayList<Object[]>();
        try {

            JSONObject jo = JSON.parseObject(s);
            JSONArray ja = jo.getJSONArray("appEventLogs");//键--》获取值：数组
            String deviceId = (String) jo.get("deviceId");

                StringBuilder sb=new StringBuilder(deviceId);
            if (ja != null && ja.size() > 0) {
                for (Object obj : ja) {//遍历数组
                    JSONObject json = (JSONObject) obj;

                    Object logType = json.get("logType");
                    Object createdAtMs = json.get("createdAtMs");

                    Object musicID = json.get("musicID");
                    Object eventId = json.get("eventId");
                    Object mark = json.get("mark");


                    Object[] arr = new Object[]{deviceId, logType, createdAtMs,
                            musicID, eventId, mark};
                    list.add(arr);
//                            sb.append(createdAtMs).append(eventId).append(logType).append(mark).append(musicID);
//                     System.out.println(createdAtMs+"---"+eventId+"---"+logType+"--mark==》"+mark+"---"+musicID);
//                          System.out.println(sb.toString());
//                    System.out.println(mark);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) throws Exception {
        //创建： 输入流
        FileInputStream in = new FileInputStream("/home/centos/qq-files/src-data/2018-07-01.log");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String res=null;
        //String res="532868065.518#192.168.23.1 #1532868067548#  200 #{\\\"appEventLogs\\\":[{\\\"createdAtMs\\\":1530412800000,\\\"eventId\\\":\\\"share\\\"}]"
        while ((res=br.readLine())!=null){
             evaluate(res.split("#")[4]);

        }

    }
}
