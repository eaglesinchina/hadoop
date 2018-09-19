package json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Json3 {

    public static List<String> getComment(String line) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        //创建流： 读取数据
//        BufferedReader br = new BufferedReader(new FileReader(path));
//        String line = null;


     //   while ((line = br.readLine()) != null) {


            //json转换
            JSONObject jo = JSON.parseObject(line.split("\t")[1]);


            JSONArray info = jo.getJSONArray("extInfoList");//["午餐","分量适中"]

            if (info != null) {
                JSONArray arr = (JSONArray) info;

                if (arr.size() != 0) {
                    //获取属性
                    JSONObject o = (JSONObject) arr.get(0);
                    JSONArray values = o.getJSONArray("values");

                    if (values != null && values.size() != 0) {
                        //System.out.println(JSON.parseArray(values.toString()));
                        for (Object value : values) {
                            list.add(value.toString());
                        }
                        System.out.println();
                    }

                }
            }
//        }

        //
//        br.close();

        return  list;

    }


}
