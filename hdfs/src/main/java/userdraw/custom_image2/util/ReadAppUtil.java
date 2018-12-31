package userdraw.custom_image2.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ReadAppUtil {
    //一行行：  读入app数据


    //属性
    static Map<String, String> appMap;

    static {
        appMap = new HashMap<String, String>();
    }


    //方法
    public static Map<String, String> getAppMap() throws Exception {
        //路径
        Path p = new Path(UserImgUtil.appPath);
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", UserImgUtil.filesystem);

        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream in = fs.open(p);

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String line = null;
        while ((line = br.readLine()) != null) {
            appMap.put(line.split("\\|")[0], line);
        }
        return appMap;
    }

//    public static void main(String[] args) throws Exception {
//        getAppMap();
//        System.out.println();
//    }

}
