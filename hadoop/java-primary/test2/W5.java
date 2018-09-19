package homework;

import org.junit.Test;

import java.io.*;

public class W5 {

    @Test
    public void test() throws IOException {

        //in.read():   byte负数--》正数
        
        //写入负数： -23
        String file="/home/centos/noodd.txt";
        OutputStream out = new FileOutputStream(file);
        out.write(new byte[]{-23});
        out.close();
        
        //读取数据
        byte[] bs = new byte[5];

        FileInputStream in = new FileInputStream(file);
        in.read(bs);
        System.out.println(bs[0]);

        int a=bs[0]& 0xff;
        System.out.println(a);


    }
}
