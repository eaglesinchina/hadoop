package lock.maptest;

import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tree {
    static int count = 1;

    @Test
    public void t1() throws Exception {

        //1,创建方法： 获取root,  left, right   key
        TreeMap<Integer, String> map = new TreeMap<>();
        int n=8;

        for (int i=0;i<n;i++){

            map.put(i, "a"+i);
        }

        Map.Entry<Integer, String> root = getRoot(map);

//        iterPre(root);
        ArrayList<Map.Entry> list = new ArrayList<>();
        list.add(root);
        iterFloor(list);
// Map.Entry<Integer, String> left = getEntryFromParent(root,"left");
//        Map.Entry<Integer, String> right = getEntryFromParent(root,"right");
//
//        System.out.println(root.getKey() +","+ root.getValue());
//        System.out.println(left.getKey() +","+ left.getValue());
//        System.out.println(right.getKey() +","+ right.getValue());

    }

    //前序遍历
    public static void iterFloor(List<Map.Entry> list) throws Exception {

        ArrayList<Map.Entry> list2 = new ArrayList<>();
        String msg = "";
        if (list.size() == 0) return;

        //左边：
        //中间： -->打印
        //右边：
        for (Map.Entry entry : list) {
            if (entry == null) continue;
            msg += entry.getKey() + "," + entry.getValue() + "  ";

            Map.Entry<Integer, String> left = getEntryFromParent(entry, "left");
            Map.Entry<Integer, String> right = getEntryFromParent(entry, "right");
            if (left != null) list2.add(getEntryFromParent(entry, "left"));
            if (right != null) list2.add(getEntryFromParent(entry, "right"));
        }

        System.out.println((count++) + "层：    -->" + msg);
        iterFloor(list2);
    }


    //前序遍历
    public static void iterPre(Map.Entry entry) throws Exception {

        if (entry != null) {

            iterPre(getEntryFromParent(entry, "left"));
            iterPre(getEntryFromParent(entry, "right"));
            System.out.println(entry.getKey() + "," + entry.getValue());
        }
//        mes+="root: "+ entry.getKey()+","+entry.getValue()+"||";
    }

    public static Map.Entry<Integer, String> getEntryFromParent(Map.Entry parent, String location) throws Exception {
        //反射： 创建对象
        Field fieldparent = parent.getClass().getDeclaredField(location);
        fieldparent.setAccessible(true);
        Map.Entry o = (Map.Entry) fieldparent.get(parent);
        return o;
    }

    public static Map.Entry<Integer, String> getRoot(Map map) throws Exception {

        //反射： 创建对象
        Field root = map.getClass().getDeclaredField("root");
        root.setAccessible(true);
        Map.Entry o = (Map.Entry) root.get(map);
        return o;
    }
}
