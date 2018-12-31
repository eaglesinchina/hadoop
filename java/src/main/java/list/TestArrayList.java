package list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestArrayList {

    @Test
    public void testArray(){

       List<Integer> list = new ArrayList<Integer>();
       int sum=100000;
        //arrayLIst:  10万 ++

        long start = System.currentTimeMillis();
        for (int i=0;i<sum;i++){
            list.add(0,i);
        }
        System.out.println("array---"+ (System.currentTimeMillis()-start));


        start=System.currentTimeMillis();
        list.get(sum/2);
        System.out.println("array---======="+ (System.currentTimeMillis()-start));

        //linkedList : 10万 ++

        start=System.currentTimeMillis();
        list=new LinkedList<Integer>();
        for (int i=0;i<sum;i++){
            list.add(0,i);
        }
        System.out.println("linked---"+ (System.currentTimeMillis()-start));
        start=System.currentTimeMillis();
        list.get(sum/2);
        System.out.println("linked=====---======="+ (System.currentTimeMillis()-start));

    }


}
