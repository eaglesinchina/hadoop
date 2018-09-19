package udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import java.util.ArrayList;
import java.util.List;

@Description(
        name="json util",
        value="parse json-->array/list",
        extended = "...."
)
public class MyJsonUtil extends UDF {


        public static List<String> evaluate(String line) {
            ArrayList<String> list = new ArrayList<String>();
            try {

                JSONObject jo= JSON.parseObject(line);
                JSONArray ja = jo.getJSONArray("extInfoList");
                if(ja != null && ja.size() > 0) {
                    for(Object obj : ja) {
                        JSONObject json = (JSONObject) obj;
                        if("contentTags".equals((json.getString("title")))) {
                            JSONArray values = json.getJSONArray("values");

                            for(Object value : values) {
                                list.add(value.toString());
                            }

                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }



            return  list;
        }

//    public static void main(String[] args) throws Exception {
//        //创建： 输入流
//        FileInputStream in = new FileInputStream("/home/centos/qq-files/temptags.txt");
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
////        String res=null;
////        while ((res=br.readLine())!=null){
////            List<String> list = evaluate(res.split("\t")[1]);
////
////            String a="";
////            for (String s : list) {
////              a+=s;
////            }
////
////            System.out.println(a);
////        }
//
//        List<String> list=evaluate("{{\"reviewPics\":null,\"extInfoList\":null,\"expenseList\":null,\"reviewIndexes\":[2],\"scoreList\":[{\"score\":5,\"title\":\"口味\",\"desc\":\"\"},{\"score\":5,\"title\":\"服务\",\"desc\":\"\"},{\"score\":5,\"title\":\"环境\",\"desc\":\"\"}]}");
//
//        String a="";
//            for (String s : list) {
//              a+=s;
//            }
//
//            System.out.println(a);
//    }
}
