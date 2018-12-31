package test2;

import org.junit.Test;

import java.io.*;

/**
 * abc--->unicode:  10byte
 *             8
 *         2 + 2
 *         2 + (2+2)
 */
public class W4 {

    @Test
    public void te() throws IOException {

        //输出流
        FileOutputStream out = new FileOutputStream("/home/centos/aa.txt");
        out.write("a".getBytes("unicode"));
        out.write("bc".getBytes("unicode"));
        out.close();

//        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/home/centos/aa.txt"), "unicode");
//        out.write("a");
//        out.close();
//
//        OutputStreamWriter out2 = new OutputStreamWriter(new FileOutputStream("/home/centos/aa.txt",true), "unicode");
//        out2.write("bc");
//        out2.close();

    }
    
    @Test
    public void read() throws IOException {
        InputStreamReader in = new InputStreamReader(new FileInputStream("/home/centos/aa.txt"), "unicode");
       
        int len=0;
        char [] bs=new char[10];
        while ((len=in.read(bs))!=-1){
            System.out.print(new String(bs,0,len));
        }
    }
}
