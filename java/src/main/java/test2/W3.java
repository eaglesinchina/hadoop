package test2;

import org.junit.Test;

/**
 * 5亿　去重－－－》计算个数： 300M
 *
 * 1M --->1024K
 * 10M--->100万K
 * 1G-->10亿K
 *
 * 0.5G -->5亿
 * 500M
 */
public class W3 {



    @Test
    public void test1(){
        int[] inputs={2,3,4,5 ,2,2,2,2};
        //定义数组：  一位： byte[8]
        // byte[int.Max]
        byte [] bs=new byte[Integer.MAX_VALUE/8+1];

        //输入的数组： hashCode
        long count=0;
        for (int i=0;i<inputs.length;i++){
            int x=inputs[i]/8;
            int y=inputs[i]%8;

            //原值
            //现在的值
            int res =   (  bs[x]   &   (1<<y) );
            if( res  == 0){
                //填入数据
                bs[x]=(byte) ( bs[x] | (1<<y) );
                count++;
            }
        }

        System.out.println(count);

    }

    @Test
    public void test(){

        System.out.println( 15 & 8
                            );
                     //  1000
//        System.out.println(Long.MAX_VALUE);
//
//        long sum=0;
//        //2305 8430 059 9246 8481
//        for(int i=0;i<Integer.MAX_VALUE;i++){
//            sum+=i;
//        }
//        System.out.println(sum);

    }
}
