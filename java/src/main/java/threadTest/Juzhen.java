package threadTest;

import org.junit.Test;

public class Juzhen {

    public static void main(String[] args) {



    }

    @Test
    public void te(){
        //测试矩阵：　乘积
        int[][] nums1=new int [][] { {1,2},
                                     {2,3}};

        int[][] nums2=new int [][] { {6,7},
                                     {8,9}};

        //求积:  行×列１，　　行×列２，　行×列３
        for (int i=0;i<nums1.length;i++) {

            for (int j=0;j<nums1.length;j++) {
                //获取行: 所有元素
                int item = nums1[i][j];


            }
            System.out.println("==========");
        }


//        for (int[] num : nums) {
//
//            for (int i : num) {
//                System.out.print(i+", ");
//
//            }
//            System.out.println("==========");
//        }
    }
}
