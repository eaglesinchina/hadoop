package logsETL.receive;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class ParseJson {

    public static void main(String[] args) throws Exception {
        String json = "1535779109.154#192.168.56.1#1535783919495#200#" +
                "{\\\"appChannel\\\":\\\"appstore\\\",\\\"appErrorLogs\\\":[{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"8.3\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"8.3\\\"}],\\\"appEventLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"duration\\\":\\\"03:12\\\",\\\"eventId\\\":\\\"listen\\\",\\\"mark\\\":\\\"1\\\",\\\"musicID\\\":\\\"猫眼里的半途\\\",\\\"osType\\\":\\\"11.0\\\",\\\"playTime\\\":\\\"1535799988223\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"duration\\\":\\\"05:11\\\",\\\"eventId\\\":\\\"listen\\\",\\\"mark\\\":\\\"1\\\",\\\"musicID\\\":\\\"フレイム・オブ･レッド (红色的火焰)\\\",\\\"osType\\\":\\\"7.1.1\\\",\\\"playTime\\\":\\\"1535799988223\\\"},{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"eventId\\\":\\\"share\\\",\\\"mark\\\":\\\"4\\\",\\\"musicID\\\":\\\"南来北往\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"playTime\\\":\\\"1535799988224\\\"}],\\\"appPageLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"nextPage\\\":\\\"main.html\\\",\\\"osType\\\":\\\"8.3\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"nextPage\\\":\\\"test.html\\\",\\\"osType\\\":\\\"1.4.0\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"nextPage\\\":\\\"test.html\\\",\\\"osType\\\":\\\"8.3\\\",\\\"pageId\\\":\\\"main.html\\\",\\\"pageViewCntInSession\\\":0,\\\"visitIndex\\\":\\\"3\\\"}],\\\"appPlatform\\\":\\\"ios\\\",\\\"appStartupLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"brand\\\":\\\"小米\\\",\\\"carrier\\\":\\\"中国铁通\\\",\\\"country\\\":\\\"america\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"network\\\":\\\"wifi\\\",\\\"osType\\\":\\\"1.2.0\\\",\\\"province\\\":\\\"shandong\\\",\\\"screenSize\\\":\\\"960 * 640\\\"}],\\\"appUsageLogs\\\":[{\\\"appChannel\\\":\\\"appstore\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"osType\\\":\\\"7.1.1\\\",\\\"singleDownloadTraffic\\\":\\\"3300\\\",\\\"singleUploadTraffic\\\":\\\"34\\\",\\\"singleUseDurationSecs\\\":\\\"78\\\"}],\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceId\\\":\\\"Device000036\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"osType\\\":\\\"1.2.0\\\"}";
//        splitTabs0(json);
        splitTabs1(json,"appUsageLogs");
//        splitTabs(json);

    }


    //阶段2：  字段拆分====>  agg表--->event表 (字段--值，  字段2--值2.....)
    public static List<String> splitTabs1(String js, String tbname) throws Exception {
        List<String> props=GetKey_tab_name.getMap_tbname_prop2(tbname);
        String[] splits = js.split("#");
        if (splits.length <5)return null;

        String json=splits[4];
        json=json.replaceAll("\\\\","");
        JSONObject jobj = JSON.parseObject(json);

        //获取属性： 小表名--->json array
        JSONArray TableLog = jobj.getJSONArray(tbname);

        //1535777886.712#192.168.56.1##200# ......
        if(TableLog ==null)return null;

        ArrayList<String> list = new ArrayList<>();
        for (Object log : TableLog) { //通过表名：  ------> 获取key, ---->obj.get( key): 获取val

            JSONObject obj = (JSONObject) log;
            //取出单条记录： 一个表对象数据
            for (int i = 0; i < props.size(); i++) {

                String propval = obj.getString(props.get(i));
                list.add(propval);
            }
        }

        System.out.println(list);
        return list;

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

            System.out.println(appChannel + ","
                    + appPlatform + ","
                    + errorBrief + ","
                    + appErrorLogs);

        }

    }
}
