package day02.homework;

import org.junit.Test;

/**
 * //传入5亿个：  int 型数据： 计算去重后的 ， 数字个数 ===》
 *  限制要求 ：  内存仅 300M
 *
 *  int a:  4byte
 *  300M   -->300*1024*1024byte:  7800万 个int ， 存放不了3亿个Int !
 *  
 *  思路： 把byte拆分为8bite使用，  形成==>7800万 * 8 byte:  6亿个位置可供使用
 *          1，定义数组容量： Integer.Max /8 +1
 *          2, 判断每个数字在数组的位置(类似x, y轴坐标)：  (x= num/8,  y=num%8 )
 *
 */
public class BillionNumsCount {

    @Test
    public void getCount(){

        int [] arr={2,34,4,Integer.MAX_VALUE,    2,Integer.MAX_VALUE};
        int[] containor= new int[Integer.MAX_VALUE / 8 +1 ];
        long count =0;


        for (int num : arr) {
            int x=num / 8;
            int y=num % 8;

            //在数组中定位---标记该数
            byte val1=  (byte) (1<< y);
            int item=containor[x];

            if( (item & val1) ==0){

                containor[x]=  (item | val1);//在该元素的 byte[y] 位置： 标记1
                count ++;
            }

        }//for
        System.out.println(count);
    }
}
