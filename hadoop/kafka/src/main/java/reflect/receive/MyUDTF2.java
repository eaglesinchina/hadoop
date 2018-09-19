package reflect.receive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.sql.*;
import java.util.*;

public class MyUDTF2 extends GenericUDTF {

    private PrimitiveObjectInspector stringOI = null;

    public static void main(String[] args) throws HiveException {

        String st="1535799989.866#192.168.56.1#1535799988330#200#{\\\"appChannel\\\":\\\"appstore\\\",\\\"appErrorLogs\\\":[{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"8.3\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"8.3\\\"}],\\\"appEventLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"duration\\\":\\\"03:12\\\",\\\"eventId\\\":\\\"listen\\\",\\\"mark\\\":\\\"1\\\",\\\"musicID\\\":\\\"猫眼里的半途\\\",\\\"osType\\\":\\\"11.0\\\",\\\"playTime\\\":\\\"1535799988223\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"duration\\\":\\\"05:11\\\",\\\"eventId\\\":\\\"listen\\\",\\\"mark\\\":\\\"1\\\",\\\"musicID\\\":\\\"フレイム・オブ･レッド (红色的火焰)\\\",\\\"osType\\\":\\\"7.1.1\\\",\\\"playTime\\\":\\\"1535799988223\\\"},{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"eventId\\\":\\\"share\\\",\\\"mark\\\":\\\"4\\\",\\\"musicID\\\":\\\"南来北往\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"playTime\\\":\\\"1535799988224\\\"}],\\\"appPageLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"nextPage\\\":\\\"main.html\\\",\\\"osType\\\":\\\"8.3\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"nextPage\\\":\\\"test.html\\\",\\\"osType\\\":\\\"1.4.0\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"nextPage\\\":\\\"test.html\\\",\\\"osType\\\":\\\"8.3\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"}],\\\"appPlatform\\\":\\\"ios\\\",\\\"appStartupLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"brand\\\":\\\"小米\\\",\\\"carrier\\\":\\\"中国铁通\\\",\\\"country\\\":\\\"america\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"network\\\":\\\"wifi\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"province\\\":\\\"shandong\\\",\\\"screenSize\\\":\\\"960 * 640\\\"}],\\\"appUsageLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"osType\\\":\\\"7.1.1\\\",\\\"singleDownloadTraffic\\\":\\\"3300\\\",\\\"singleUploadTraffic\\\":\\\"34\\\",\\\"singleUseDurationSecs\\\":\\\"78\\\"}],\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceId\\\":\\\"Device000036\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"osType\\\":\\\"1.2.0\\\"}\n" +
                "1535799990.549#192.168.56.1#1535799989022#200#{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appErrorLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"errorBrief\\\":\\\"at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)\\\",\\\"errorDetail\\\":\\\"at cn.lift.dfdfdf.control.CommandUtil.getInfo(CommandUtil.java:67) at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) at java.lang.reflect.Method.invoke(Method.java:606)\\\",\\\"osType\\\":\\\"11.0\\\"}],\\\"appEventLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"duration\\\":\\\"04:21\\\",\\\"eventId\\\":\\\"listen\\\",\\\"mark\\\":\\\"1\\\",\\\"musicID\\\":\\\"二十二\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"playTime\\\":\\\"1535799988916\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"eventId\\\":\\\"favourite\\\",\\\"mark\\\":\\\"3\\\",\\\"musicID\\\":\\\"讲不出再见\\\",\\\"osType\\\":\\\"8.3\\\",\\\"playTime\\\":\\\"1535799988917\\\"}],\\\"appPageLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"nextPage\\\":\\\"test.html\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"4\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"nextPage\\\":\\\"list.html\\\",\\\"osType\\\":\\\"8.3\\\",\\\"pageId\\\":\\\"list.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"2\\\"}],\\\"appPlatform\\\":\\\"ios\\\",\\\"appStartupLogs\\\":[{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"brand\\\":\\\"三星\\\",\\\"carrier\\\":\\\"中国电信\\\",\\\"country\\\":\\\"america\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"network\\\":\\\"wifi\\\",\\\"osType\\\":\\\"11.0\\\",\\\"province\\\":\\\"guangxi\\\",\\\"screenSize\\\":\\\"480 * 320\\\"}],\\\"appUsageLogs\\\":[{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"android\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"singleDownloadTraffic\\\":\\\"3400\\\",\\\"singleUploadTraffic\\\":\\\"33\\\",\\\"singleUseDurationSecs\\\":\\\"78\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"singleDownloadTraffic\\\":\\\"35\\\",\\\"singleUploadTraffic\\\":\\\"459\\\",\\\"singleUseDurationSecs\\\":\\\"78\\\"}],\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceId\\\":\\\"Device000002\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"osType\\\":\\\"1.4.0\\\"}";
        MyUDTF2 udf = new MyUDTF2();
        udf.process(new Object[]{
                st
        });
    }

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {

        //获取对象检查器
        List<? extends StructField> allStructFieldRefs = argOIs.getAllStructFieldRefs();

       stringOI   = (PrimitiveObjectInspector) allStructFieldRefs.get(0).getFieldObjectInspector();
        if (allStructFieldRefs.size() != 1) {
            throw new UDFArgumentException("needs  only one argument");
        }

        if (stringOI.getCategory() != ObjectInspector.Category.PRIMITIVE
                && stringOI.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("need  a string  parameter....");
        }

        // 输出格式（inspectors） -- 有两个属性的对象
        List<String> fieldNames = new ArrayList<String>();
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("c1");
        fieldNames.add("c2");
        fieldNames.add("c3");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }


    @Override
    public void process(Object[] record) throws HiveException {
        System.out.println("进入process....");

        //final String name = stringOI.getPrimitiveJavaObject(record[0]).toString();
//得到一行数据
        String name = (String)  stringOI. getPrimitiveJavaObject(record[0]);
        System.out.println("stringOI==null"+(stringOI==null));
        System.out.println("name---"+name);
        // 忽略null值与空值
        if (name == null || name.isEmpty()) {
            return ;
        }

        List<HashMap<String, Map<String, String>>> maps=null;
        try {
            //解析json数据
             maps = splitTabs(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
       // String[] fields = name.split("#");
        if (maps!=null)
        for (HashMap<String, Map<String, String>> map : maps) {
            //appStartupLogs,   Map( brand,魅族 )
            //appStartupLogs,   Map( network=cell )

            if (map!=null)
            for (String key_tbname : map.keySet()) {
                Map<String, String> columns_key_val_map = map.get(key_tbname);
                    forward(new Object[]{key_tbname,  columns_key_val_map});

//                if (columns_key_val_map!=null)
//                for (String column_key : columns_key_val_map.keySet()) {
//                    String column_val = columns_key_val_map.get(column_key);
//
////                    forward(new Object[]{ key_tbname, column_key,column_val });
//                    System.out.println(key_tbname+","+column_key+","+column_val);
//                }

            }

        }

    }

    @Override
    public void close() throws HiveException {

    }

//==================================================
    //阶段3：  字段拆分====>  从map中： 取出key (表名), vla( 字段值）
    public  static List< HashMap<String, Map<String,String>> >  splitTabs(String json) throws Exception {
        HashMap<String, List<String>> map = getMap_tbname_prop();
        List< HashMap<String, Map<String,String>> > results=new ArrayList<>();

        Set<Map.Entry<String, List<String>>> entries = map.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            //获取表名============
            String key = entry.getKey();

            //获取所有字段------------
            List<String> values = entry.getValue();
            HashMap<String, Map<String, String>> map1 = splitTabs1(json, key, values);
            results.add(map1);
        }
        return results;
    }




    //阶段2：  字段拆分====>  agg表--->event表 (字段--值，  字段2--值2.....)
    private static HashMap<String, Map<String,String>> splitTabs1(String js, String tbname, List<String> props) {
        String[] splits = js.split("#");
        if (splits.length <5)return null;

        String json=splits[4];
        json=json.replaceAll("\\\\","");

        JSONObject jobj = JSON.parseObject(json);
        //遍历数组： 取出对象的属性---->errorBrief, errorDetail
        //           父类属性： "appChannel"
        //"appPlatform
        //"appVersion"
        //"deviceStyle
        //"osType": "

        //  "appChannel": "appstore",

        //"errorBrief"
        //"errorDetail

        //获取属性： 小表名--->json array
        JSONArray TableLog = jobj.getJSONArray(tbname);

        //1535777886.712#192.168.56.1##200# ......
        if(TableLog ==null)return null;
        HashMap<String, String> vals = new HashMap<>();

        for (Object log : TableLog) { //获取表名：  ------> 获取key, ---->obj.get( key): 获取val

            JSONObject obj = (JSONObject) log;
            //取出单条记录： 一个表对象数据
            for (int i = 0; i < props.size(); i++) {

                String propval = obj.getString(props.get(i));
//                System.out.println(tbname + ",   "+props.get(i)+"=" + propval);
                vals.put(props.get(i), propval);
            }
            //System.out.println("---------");
        }

        //返回值
        HashMap<String, Map<String,String>> res = new HashMap<>();

        res.put(tbname,vals);
        return  res;
    }







    //阶段1：  字段拆分====>  agg表--->event表 (字段--值，  字段2--值2.....)
    private static void splitTabs0(String json) {
        JSONObject jobj = JSON.parseObject(json);

        //获取属性： 小表名--->json array
        JSONArray errorLogs = jobj.getJSONArray("appErrorLogs");

        //遍历数组： 取出对象的属性---->errorBrief, errorDetail
        //           父类属性： "appChannel"
        //"appPlatform
        //"appVersion"
        //"deviceStyle
        //"errorBrief"
        //"errorDetail
        //"osType": "
        for (Object errorLog : errorLogs) { //获取表名：  ------> 获取key, ---->obj.get( key): 获取val

            JSONObject obj = (JSONObject) errorLog;

            String appChannel = obj.getString("appChannel");
            String appPlatform = obj.getString("appPlatform");

            String errorBrief = obj.getString("errorBrief");
            String appErrorLogs = obj.getString("appErrorLogs");
//
//            System.out.println(appChannel + ","
//                    + appPlatform + ","
//                    + errorBrief + ","
//                    + appErrorLogs);

        }

    }




    //==========
    //初始化： 属性
    static  String url, username , pwd ;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        url="jdbc:mysql://master:3306/db";
        username="root";
        pwd="daitoue";
    }


    /**
     * 获取： 一个表-----》所有字段
     * @param tbname
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<String> getTbNameProp(String tbname) throws SQLException, ClassNotFoundException {
        Connection conn = DriverManager.getConnection(url, username, pwd);
        //创建stam对象
        String sql="select * from table_shadow where tablename=?";
        PreparedStatement prestam = conn.prepareStatement(sql);

        prestam.setString(1, tbname);

        ResultSet resultSet = prestam.executeQuery();

        ArrayList<String> props = new ArrayList<>();
        //遍历结果
        while (resultSet.next()){
            int len = resultSet.getMetaData().getColumnCount();
            //取出所有值----->表的字段
            for(int i=2;i<=len;i++){
                String colum = resultSet.getString(i);
                if (null !=colum  ){

                    props.add(colum);
//                    System.out.println(colum);
                }

            }
        }

        return props;
    }

    /**
     * 获取： 所有表名
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getTbname() throws Exception{
        Connection conn = DriverManager.getConnection(url, username, pwd);
        //创建stam对象
        String sql="select tablename from table_shadow ";
        PreparedStatement prestam = conn.prepareStatement(sql);

        ResultSet resultSet = prestam.executeQuery();
        //取出结果
        // HashMap<String, List<String>> map = new HashMap<>();
        ArrayList<String> tbs = new ArrayList<>();

        while (resultSet.next()) {
            String tbname = resultSet.getString(1);
            tbs.add(tbname);
//            System.out.println("表名--->"+tbname);
        }

        return tbs;
    }

    /**
     * 获取所【表记录】：  表名， 所有字段
     * @throws Exception
     */

    public static HashMap<String, List<String>> getMap_tbname_prop() throws Exception{
        HashMap<String, List<String>> map = new HashMap<>();

        //表名
        ArrayList<String> tbnames = getTbname();

        //表： 所有字段
        for (String tbname : tbnames) {

            ArrayList<String> props = getTbNameProp(tbname);

            //组合map
            map.put(tbname,props);
//            System.out.println(tbname+""+props);
        }

        return map;
    }
}
