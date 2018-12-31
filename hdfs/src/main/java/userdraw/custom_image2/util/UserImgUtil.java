package userdraw.custom_image2.util;

import java.io.IOException;
import java.util.Properties;

public class UserImgUtil {
    //加载配置文件
    static Properties prop = new Properties();

    static {
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("UserImg.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //属性
    //user_id=0
    //user_appid=15
    //user_time=11
    //user_duration=12
    //
    //#app信息： 相关属性
    //app_id=0
    public static int  user_id = Integer.parseInt(prop.getProperty("user_id"));
    public static int user_appid =Integer.parseInt( prop.getProperty("user_appid"));
    public static int user_time = Integer.parseInt( prop.getProperty("user_time"));

    public static int user_duration = Integer.parseInt(prop.getProperty("user_duration"));
    public static int app_id = Integer.parseInt(prop.getProperty("app_id"));
    public  static String filesystem=prop.getProperty("filesystem");
    public  static String appPath=prop.getProperty("app_path");

//    public static void main(String[] args) {
//        System.out.println(user_appid);
//    }

}
