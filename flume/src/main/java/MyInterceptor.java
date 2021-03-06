import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyInterceptor implements Interceptor {


    private String type;
    private String table;
    private String url;
    private String user;
    private String pass;
    private String driver;

    public List<String> list;

    //=====================
    public static void main(String[] args) {

        Event event = new Event(){
            byte[] bytes;

            public Map<String, String> getHeaders() {
                return null;
            }

            public void setHeaders(Map<String, String> map) {

            }

            public byte[] getBody() {
                return bytes;
            }

            public void setBody(byte[] bytes) {
                this.bytes=bytes;
            }
        };

        String info="{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appErrorLogs\\\":[{\\\"createdAtMs\\\":1531404180000,\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\"},{\\\"createdAtMs\\\":1531394940000,\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\"}],\\\"appEventLogs\\\":[null],\\\"appPageLogs\\\":[{\\\"createdAtMs\\\":1531319400000,\\\"logType\\\":\\\"page\\\",\\\"nextPage\\\":\\\"list.html\\\",\\\"pageId\\\":\\\"list.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"4\\\"},{\\\"createdAtMs\\\":1531298700000,\\\"logType\\\":\\\"page\\\",\\\"nextPage\\\":\\\"list.html\\\",\\\"pageId\\\":\\\"list.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"2\\\"}],\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appStartupLogs\\\":[{\\\"brand\\\":\\\"三星\\\",\\\"carrier\\\":\\\"中国移动\\\",\\\"country\\\":\\\"america\\\",\\\"createdAtMs\\\":1531302600000,\\\"logType\\\":\\\"startup\\\",\\\"network\\\":\\\"3g\\\",\\\"province\\\":\\\"guangxi\\\",\\\"screenSize\\\":\\\"480 * 320\\\"},{\\\"brand\\\":\\\"联想\\\",\\\"carrier\\\":\\\"中国移动\\\",\\\"country\\\":\\\"america\\\",\\\"createdAtMs\\\":1531274160000,\\\"logType\\\":\\\"startup\\\",\\\"network\\\":\\\"4g\\\",\\\"province\\\":\\\"shanxi\\\",\\\"screenSize\\\":\\\"960 * 640\\\"}],\\\"appUsageLogs\\\":[{\\\"createdAtMs\\\":1531285440000,\\\"logType\\\":\\\"usage\\\",\\\"singleDownloadTraffic\\\":\\\"45900\\\",\\\"singleUploadTraffic\\\":\\\"35\\\",\\\"singleUseDurationSecs\\\":\\\"456\\\"},{\\\"createdAtMs\\\":1531287420000,\\\"logType\\\":\\\"usage\\\",\\\"singleDownloadTraffic\\\":\\\"4\\\",\\\"singleUploadTraffic\\\":\\\"4\\\",\\\"singleUseDurationSecs\\\":\\\"456\\\"}],\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceId\\\":\\\"Device000010\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"osType\\\":\\\"11.0\\\"}";
        event.setBody(info.getBytes());


        MyInterceptor i=  new MyInterceptor(//appEventLogs   appPageLogs  appStartupLogs
                "appEventLogs",
                "table_shadow",
                "jdbc:mysql://localhost:3306/mysql2HDFS",
                "root",
                "daitoue",
                "com.mysql.jdbc.Driver");
        i.intercept( event);
    }
//=====================
    public MyInterceptor(String type, String table, String url, String user, String pass, String driver) {
        this.type = type;
        this.table = table;
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.driver = driver;

        list = new ArrayList<String>();

        //获取指定类型的所有字段
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement st = conn.createStatement();

            String sql = "select * from " + table + " where tablename=" + "'" + type + "'";

            ResultSet rs = st.executeQuery(sql);

            //通过rs获取到指定行的所有字段
            ResultSetMetaData rsm = rs.getMetaData();
            int columnCount = rsm.getColumnCount();

            rs.next();

            //从2 开始，是因为字段名称在第二列之后，第一列为日志类型
            for (int i = 2; i <= columnCount; i++) {
                String keyword = rs.getString(i);
                if (keyword != null) {
                    list.add(keyword);
                }
            }
            st.close();
            rs.close();
            conn.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 通过日志类型解析相应字段，并以\t作为分隔
     * 解析一次返回一个event
     * event中每行数据均为日志聚合体
     */
    public Event intercept(Event event) {
        String line = new String(event.getBody());

        String newJson = line.replaceAll("\\\\", "");

        JSONObject jo = JSON.parseObject(newJson);

        //解析eventlog
        JSONArray jArray = jo.getJSONArray(type);

        StringBuffer sb2 = new StringBuffer();
        if (jArray.size() >= 1) {
            for (Object o : jArray) {
                //将Array中所有json强转
                StringBuffer sb = new StringBuffer();
                JSONObject jo2 = (JSONObject) o;
                if(jo2==null) break;

                for (String keyword : list) {
                    String word = jo2.get(keyword)+"";
                    sb.append(word + "\t");
                }
                if (sb.length() != 0) {
                    String newLine = sb.toString();
                    String newLine2 = newLine.substring(0, newLine.length() - 1);
                    //清空stringbuffer
                    sb.setLength(0);

                    sb2.append(newLine2 + ";");
                }
            }
        }


        String newStr2 = "";
        if (sb2.length() != 0) {
            String newStr = sb2.toString();
            newStr2 = newStr.substring(0, newStr.length() - 1);
            //清空stringbuffer
            sb2.setLength(0);
        }
        System.out.println(newStr2);
        event.setBody(newStr2.getBytes());
        return event;
    }


    /**
     * Delegates to {@link #intercept(Event)} in a loop.
     *
     * @param events
     * @return
     */
    public List<Event> intercept(List<Event> events) {
        for (Event event : events) {
            intercept(event);
        }
        return events;
    }

    public void initialize() {

    }

    public void close() {
        // no-op
    }

    /**
     * Builder which builds new instances of the MyInterceptor.
     */
    public static class Builder implements Interceptor.Builder {

        private String type;
        private String table;
        private String url;
        private String user;
        private String pass;
        private String driver;


        public void configure(Context context) {
            //日志类型没有默认值
            type = context.getString(Constants.LOG_TYPE);
            //
            table = context.getString(Constants.TABLE_NAME, Constants.DEFAULT_TABLE_NAME);

            url = context.getString(Constants.URL, Constants.DEFAULT_URL);

            user = context.getString(Constants.USERNAME, Constants.DEFAULT_USERNAME);

            pass = context.getString(Constants.PASSWORD, Constants.DEFAULT_PASSWORD);

            driver = context.getString(Constants.DRIVER, Constants.DEFAULT_DRIVER);

        }

        public Interceptor build() {
            return new MyInterceptor(type, table, url, user, pass, driver);
        }
    }

    //使用内部类，定义关键字
    public static class Constants {
        //日志类型，非空
        public static final String LOG_TYPE = "logType";
        //表名
        public static final String TABLE_NAME = "tablename";
        public static final String DEFAULT_TABLE_NAME = "table_shadow";

        //url
        public static final String URL = "url";
        public static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/big12";

        //用户名
        public static final String USERNAME = "username";
        public static final String DEFAULT_USERNAME = "root";

        //密码
        public static final String PASSWORD = "password";
        public static final String DEFAULT_PASSWORD = "daitoue";

        //class
        public static final String DRIVER = "driver";
        public static final String DEFAULT_DRIVER = "com.mysql.jdbc.Driver";


    }

}