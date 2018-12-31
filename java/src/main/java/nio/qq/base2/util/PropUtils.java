package nio.qq.base2.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtils {
    private static Properties prop;

    static {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties");
        prop=new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getStringVal(String name){
        return prop.getProperty(name);
    }

    public static int getIntVal(String name){
        return Integer.parseInt(prop.getProperty(name));
    }//

    public static void main(String[] args) {
        getIntVal("qq.server.threadpool.cores");
    }
}
