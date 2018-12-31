package redBlack;

import org.junit.Test;

import java.util.TreeMap;

public class TestTreemap {

    @Test
    public void test(){

        //测试： 红黑数---treemap,  内部存储机制
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        map.put(1,"a");
        map.put(4,"d");
        map.put(5,"e");

        map.put(2,"b");
        map.put(3,"c");

        //中序遍历
        for (Integer key : map.keySet()) {
            String val = map.get(key);

            System.out.println(key+","+val);

        }


    }
}
