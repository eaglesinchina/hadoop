package reflect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ValUtil {

    //获取dict文件数据--->map<String, List<string>  >

  static Map<String,List<String>> map= new HashMap<String,List<String>>();

//    public static void main(String[] args) {
//        new ValUtil();
//    }

   static {
       //读取文件信息,//加载到map
       try {
           getFileVals();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    private static Map<String, List<String>> getFileVals() throws IOException {
        //加载文件： 流
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("dictionary.dat");
        //缓冲流： 读取一行数据---》判断是否[...]
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line=null;
        ArrayList<String> list=null;

        while ((line=br.readLine())!=null){

            //判断是否key [...]
            if (line.contains("[")){
                //预备： 容器
                list = new ArrayList<String>();
                map.put(line, list);
            }
            else {
                //放值
                list.add(line);
            }
        }//while

        //关闭资源
        br.close();
        in.close();

        return map;
    }


}
