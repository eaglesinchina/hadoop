package json.mpr2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<String> getComment(String line){
        ArrayList<String> list = new ArrayList<String>();
        //json转换
        JSONObject jo = JSON.parseObject(line);


        JSONArray arr = jo.getJSONArray("extInfoList");//["午餐","分量适中"]

        if (arr != null) {

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
        }//if

        return list;

    }
}
