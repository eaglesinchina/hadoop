package test2;

import org.junit.Test;

public class W6 {

    private long count1(String str) {
        int count=0;

        for (int i=0;i<str.length();i++){
            if(((str.charAt(i))+"" ).equalsIgnoreCase("1")){
                count++;
            }
        }
        return count;

    }

    @Test
    public void te(){
        int[] nums = {23, 3, 3, 3, 3, 3};
        //创建容器
        byte[] bs=new byte[100];

        //填入数据： 找到存放位置， 1
        for (int i=0;i<nums.length;i++){
            int x=nums[i]/8;
            int y=nums[i]%8;

           // bs[x]--->数据： 为y
            bs[x]=(byte)(y + bs[x]);
        }
        //统计： 1总数
        long count=0;
        for (int i=0;i<bs.length;i++){

            String st = Integer.toBinaryString(bs[i]);
            count +=count1(st);
        }

        System.out.println(count);
    }



//        byte[][] bs=new byte[nums.length+1][8];

        //0 ,1, 2, 3, 4, 5 ,6 ,7,  8 , 9
        //1
        //2
        //3
        //4
        //5
        //6

//        //输入值
//        long count=0;
//        for(int i=0;i<nums.length;i++){
//            int x=nums[i]/8;
//            int y=nums[i]%8;
//
//            if(bs[x][y] !=0){
//                bs[x][y]=1;
//                count++;
//            }
//
//        }




}
