package logsETL.utils;

import logsETL.logs.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class GenLogUtil {

    public <T> T genLog(Class<T> clazz) throws Exception {
        Map<String, List<String>> map = DictUtil.map;

        List<String> name_time = MysqlUtil.randomMusic();

        T t1 = clazz.newInstance();


        //如果是子类，把base的字段也要加上
        if (t1 instanceof AppBaseLog) {
            //获取所有字段baseLog+EventLog
            Class clazz2 = AppBaseLog.class;

            Field[] fields2 = clazz2.getDeclaredFields();
            for (Field field : fields2) {
                //设置访问权限
                field.setAccessible(true);
                if (!(field.getModifiers() + "").contains("5")) {
                    String value = DictUtil.randomValue(map, field.getName().toLowerCase());
                    field.set(t1, value);
//                    System.out.println("父类属性----"+field.getName());
                            

                }
            }
//            System.out.println("=====");
        }

        //如果不是eventLog类型
        //获取所有字段baseLog+EventLog
        Field[] fields2 = clazz.getDeclaredFields();
        for (Field field : fields2) {
            //设置访问权限
            field.setAccessible(true);
            if(field.getType() == String.class){
                String value = DictUtil.randomValue(map, field.getName().toLowerCase());
                field.set(t1, value);
            }
        }


        //如果t1是event日志类型，那么直接设置Event的所有字段

        if (t1 instanceof AppEventLog) {

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //设置访问权限
                field.setAccessible(true);
                //对象设置字段值
                String value = DictUtil.randomValue(map, field.getName().toLowerCase());
                field.set(t1, value);
                if (name_time.size() != 0) {
                    ((AppEventLog) t1).setMusicID(name_time.get(0));
                    ((AppEventLog) t1).setPlayTime(System.currentTimeMillis() + "");
                }
            }
            //如果事件为跳过或主动播放，则设定听歌时长
            if (((AppEventLog) t1).getEventId().equals("play") || ((AppEventLog) t1).getEventId().equals("listen")) {
                if (name_time.size() != 0) {
                    String duration = name_time.get(1);
                    ((AppEventLog) t1).setDuration(duration);
                }
            }
            String mark = parseMark(((AppEventLog) t1).getEventId());
            ((AppEventLog) t1).setMark(mark);
        }



//        String s = JSON.toJSONString(t1);
//        return s;
        return t1;


    }

    public static String parseMark(String event) {
        switch (event) {
            case "share":
                return "4";
            case "favourite":
                return "3";
            case "play":
                return "2";
            case "listen":
                return "1";
            case "skip":
                return "-1";
            case "nofavourite":
                return "-4";
            case "black":
                return "-5";
            default:
                return "0";
        }
    }

//    public static void main(String[] args) throws Exception {
//
//        List<String> name_time = MysqlUtil.randomMusic();
//        System.out.println(name_time);
//        GenLogUtil genLogUtil = new GenLogUtil();
//        for (int i = 0; i < 50; i++) {
//            String s = genLogUtil.genLog(AppEventLog.class);
//            String s2 = genLogUtil.genLog(AppErrorLog.class);
//            String s3 = genLogUtil.genLog(AppStartupLog.class);
//            String s4 = genLogUtil.genLog(AppPageLog.class);
//            System.out.println(s);
//            System.out.println(s2);
//            System.out.println(s3);
//            System.out.println(s4);
//            System.out.println("===============================================");
//        }
//    }
}
