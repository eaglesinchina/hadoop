package userdraw.custom_image3.util;

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
    static Configuration conf = new Configuration();
    //属性
    static Map<String, String> appMap, userAppDataMap;

    static {
        conf.set("fs.defaultFS", UserImgUtil.filesystem);
        appMap = new HashMap<String, String>();
        userAppDataMap = new HashMap<String, String>();
    }


    //方法
    public static Map<String, String> getAppMap() throws Exception {
        //路径
        Path p = new Path(UserImgUtil.appPath);
        FileSystem fs = FileSystem.get(conf);
        FSDataInputStream in = fs.open(p);

        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        //新数据： 读取appid文件---》整合为有效数据
        String line = null;
        while ((line = br.readLine()) != null) {
            appMap.put(line.split("\\|")[0], line);
        }
        return appMap;
    }

    //方法
    public static Map<String, String> getUserAppDataMap() throws Exception {
        //路径
        Path p = new Path(UserImgUtil.userAppDataPath);
        FileSystem fs = FileSystem.get(conf);
        if(!fs.exists(p)) return null;

        FSDataInputStream in = fs.open(p);
        //BufferedReader br = new BufferedReader(new InputStreamReader(in));
        //新数据+  旧数据==》 整合为叠加后的数据
        String line = null;
        while ((line = in.readLine()) != null) {
            String[] msgs= line.split("\t");
            userAppDataMap.put(msgs[0],msgs[1]);// (userid,  app使用时间数据）
        }
        return userAppDataMap;
    }

//    public static void main(String[] args) throws Exception {
//        getAppMap();
//        System.out.println();
//    }

}
