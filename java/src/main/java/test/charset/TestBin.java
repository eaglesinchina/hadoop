package test.charset;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestBin {


    @Test
    public void str2Md5() throws NoSuchAlgorithmException {
        String s="abc";
        //900150983cd24fb0d6963f7d28e17f72
        String res="";
        //900150983cd24fb0d6963f7d28e17f72
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] digest = md5.digest(s.getBytes());

        char chs[]={'0','1','2','3','4','5',
                '6','7','8','9','a',
                'b','c','d','e','f'};

        //和16 运算
        for(int i=0;i<digest.length;i++){

            byte b = digest[i];

            byte b1 = (byte) (b >> 4);
            char c= chs[b1 & 0xf];
            res+=c;

            byte b2 = (byte) (b );
            char c2= chs[b2 & 0xf];
            res+=c2;
        }
        System.out.println(res);
    }

    @Test
    public void int2Hex(){
        int a=-192;
        char chs[]={'0','1','2','3','4','5',
                '6','7','8','9','a',
                'b','c','d','e','f'};


        //4*8= 32位　　　8421-->16: 4位
        // 8 个数
        String s="";
        for(int i=28;i>=0;i-=4){
            byte b=(byte)(a>>i);

            char c= chs[b & 0xf];
            s+=c;
            System.out.println(c);
        }

        System.out.println("-------->"+s);
        System.out.println(Integer.toHexString(a));

    }

    @Test
    public void int2Bin(){
        int a=192;

        String st="";
        for(int i=31;i>=0;i--){
            byte b=(byte) (a>>i & 1);
            st+= b;
            System.out.println(st);

        }
    }


@Test
    public void t(){
        for(int i=0;i<20;i++){
            System.out.println(i+"-->"+Integer.toHexString(i));
        }    
    }


}
