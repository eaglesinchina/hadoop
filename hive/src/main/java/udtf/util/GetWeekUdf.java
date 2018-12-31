package udtf.util;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class GetWeekUdf extends UDF {

    @Test
    public int evaluate(String time) throws ParseException {

        //string--->date
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = format.parse("2018-9-3");
//
//        System.out.println(date.getTime());

//        //创建calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.parseLong(time)));
        
        
        //获取： 星期
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(week);


        //判断周几
        if(week==1 || week ==7){
            return 1;
        }
        return 2;

    }


}
