package test.charset;

import org.junit.Test;

import java.io.*;

public class ReadFile {

    @Test
    public void te() throws IOException {

        //文件输入
        FileInputStream fin = new FileInputStream("/home/centos/qq-files/8888.txt");


        //读取
        byte[] buf = new byte[10];
        int len=0;
        while((len=fin.read(buf))!=-1){
            System.out.println(new String(buf,0,len));

        }

        fin.close();

    }
    
    
    @Test
    public void test() throws IOException {
        InputStreamReader fin = new InputStreamReader(new FileInputStream("/home/centos/qq-files/8888.txt"),"unicode");
        BufferedReader br = new BufferedReader(fin);

        int read = 0;
        
        while ((read=br.read())!=-1){
            System.out.println((char)read);
        }
        
        


    }
}
