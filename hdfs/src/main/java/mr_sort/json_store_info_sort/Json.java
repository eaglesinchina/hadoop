package mr_sort.json_store_info_sort;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Json {

    public static void main(String[] args) {
        //定义: json字符串
        String js = "{\"name\":\"李四\",\"age\":\"23\"}";

        //json数据：  获取属性
        JSONObject jo = JSON.parseObject(js);

        String name = jo.getString("name");
        int age = jo.getIntValue("age");

        System.out.println(name+"---"+age);

    }
}
