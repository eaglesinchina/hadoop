package json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Json2 {

    public static void main(String[] args) {
        //定义: json字符串
        String js="{per:[{'name':'李四', 'age':12},    {'name':'张三',  'age':23}]  }";

        //json数据：  获取属性
        JSONObject jso = JSON.parseObject(js);
        JSONArray arr = jso.getJSONArray("per");
        int size = arr.size();

        for(int i=0;i<size;i++){
            JSONObject o2=(JSONObject)arr.get(i);
            System.out.println(  o2.get("name")   );
        }

//        for ( Object o: arr){
//            JSONObject jo2=(JSONObject)o;
//            String name = jo2.getString("name");
//            int age = jo2.getIntValue("age");
//
//            System.out.println(name+"--"+age);
//
//        }
        


    }
}
