package nio.qq.base.util;

import org.junit.Test;

public class NumberUtils {

    //byte-->int
    public static int byte2Int(byte[] arr){

        return  (arr[0] & 0xff) <<24 |
                (arr[1] & 0xff) <<16 |
                (arr[2] & 0xff) <<8 |
                (arr[3] & 0xff) <<0 ;
    }
    //int--->byte
    public static byte[] int2byte(int a){
        byte[] arr = new byte[4];

        arr[0]= (byte)(a>>24);
        arr[1]= (byte)(a>>16);
        arr[2]= (byte)(a>>8);
        arr[3]= (byte)(a>>0);

        return arr;
    }
    
    @Test
    public void test(){
        System.out.println(byte2Int(int2byte(-1)));
    }
}
