package test.charset;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestCharset2 {
    @Test
    public void test(){

        //打印所有 char
        for (int i=Character.MIN_VALUE;i<Character.MAX_VALUE;i++){
            System.out.println(i + "=" + (char) i + ", ");
        }
    }

    @Test
    public void testInt2Byte(){
        //int --->4 字节  0  4 8 12
        int i=10;
        byte[] bs = new byte[4];
        
        byte b=(byte)(i>>1);


        System.out.println(b);
//        byte b1 = (byte)(i >> 12);
//        byte b2 = (byte)(i >> 8);
//        byte b3 = (byte)(i >> 4);
//        byte b4 = (byte)(i >> 0);

//        System.out.println(b1);
//        System.out.println(b2);
//        System.out.println(b3);
//        System.out.println(b4);





    }

    @Test
    public void testCharset1() throws UnsupportedEncodingException {

        //输入中文----->各字符集： bytes表示

        String charset="unicode";
        String st="中";

        String unicodeBytes = getUnicodeBytes(st, charset);
        System.out.println(unicodeBytes);

        //调用方法
//        byte[] bytes = getBytes(st, charset);
//        for (byte aByte : bytes) {
//            System.out.println(aByte);
//        }
//
//
//        System.out.println("==========");
//        String st2="中";
//        byte[] bytes2 = getBytes(st2, charset);
//        for (byte aByte : bytes2) {
//            System.out.println(aByte);
//        }

    }

    public byte[] getBytes(String st,String charset) throws UnsupportedEncodingException {
        byte[] bytes = st.getBytes(charset);

        return bytes;

    }

    public String getUnicodeBytes(String st,String charset) throws UnsupportedEncodingException {
        byte[] bytes = getBytes(st,charset);
        int len = bytes.length;
        //截去头两位：  保留后面byte--->0 97  || 78 45
        //                      ---->int 转16进制

        String s="\\u";

        for(int i=2;i<len;i++){
            //---->int 转16进制
            //0 97  || 78 45

            String str = Integer.toHexString(bytes[i]);
            s+=str;

        }
        return s;
    }
}
