package fastcopy;

import java.io.*;

public class JavaIOcp {
    public static void main(String[] args) throws IOException {

        File file = new File("/home/centos/qq-files/logs.zip");
        //输入流
        FileInputStream in = new FileInputStream(file);

        //输出流
        FileOutputStream out = new FileOutputStream("/home/centos/logs2.zip");
        //拷贝
        long start=System.currentTimeMillis();
        int len=0;
        byte[] buf=new byte[1024];
        while ((len=in.read(buf))!=-1){
            out.write(buf,0,len);
        }

        //关闭资源
        in.close();
        out.close();
        System.out.println("java-io-copy-->"+(System.currentTimeMillis()-start));
    }
}
