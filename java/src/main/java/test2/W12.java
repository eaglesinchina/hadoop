package test2;

import org.junit.Test;

import java.io.*;

public class W12 {
    //long --->byte[]
    //byte[] --->long

    @Test
    public void testLong2bytes() {
        long a=-12955l;
        bytes2long(long2bytes(a));
    }

    public byte[] long2bytes(long a){
        //long--->8 位:   byte[8]-->ｂｙｔｅ值
        byte[] bs = new byte[8];

        for(int i=7;i>=0;i--){
            bs[i]= (byte) (a>>(8*i)) ;
            System.out.println(bs[i]);
        }
        System.out.println("long-->byte");
        System.out.println(Long.toBinaryString(a));
        //
        return bs;
    }


    public void bytes2long(byte[] bs){

        long sum=0;
        for(int i=7;i>=0;i--){
            long tmp=(long) 0xFF;
            long x=  ( tmp &  bs[i])   <<  (8*i) ;
            sum +=x;
            System.out.println(x);
        }

        System.out.println("bytes->long:  "+sum);
        System.out.println(Integer.toBinaryString(0xff));
    }

    public static void main(String[] args) {
//        new ObjectOutputStream(new FileOutputStream("")).writeLong();
//        new ObjectInputStream(new FileInputStream("")).readLong();
    }
}
