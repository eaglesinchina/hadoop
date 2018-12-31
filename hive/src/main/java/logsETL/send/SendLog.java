package logsETL.send;

import logsETL.utils.GenLogAggUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;

public class SendLog {

    public static void main(String[] args) throws Exception {
        //文件输出
//        FileOutputStream out = new FileOutputStream("/home/centos/log.txt");

        Random r = new Random();

        //格式化数字串
        DecimalFormat df = new DecimalFormat("000000");

        //发送的日志个数
        for (int i = 0; i < 50; i++) {
//        while(true){
            String deviceId = "Device"+ df.format(r.nextInt(100)+1);
            String s = GenLogAggUtil.genLogAgg(deviceId);

//            out.write(s.getBytes());
//
//            out.write("\n\n".getBytes());
//            out.flush();

            Thread.sleep(100);
            System.out.println(s);


            String strUrl = "http://192.168.56.102:80/index.html";
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义post请求类型
            conn.setRequestMethod("POST");
            conn.setRequestProperty("content-Type","application/json");
            conn.setRequestProperty("client_time", System.currentTimeMillis()+"");
            //允许输出到服务器
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(s.getBytes());
            os.close();
            System.out.println(conn.getResponseCode());

        }



    }
}