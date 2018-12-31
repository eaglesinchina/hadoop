package threadTest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 红黑数： 层序遍历
 *
 * a
 * bc  d e
 * 123  34567
 */
public class RedBlackIter {

    public static void main(String[] args) throws Exception {
        //生成数据， 遍历
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        map.put(1 , "tom1");
        map.put(2 , "tom1");
        map.put(3 , "tom1");
        map.put(4 , "tom1");
        map.put(5 , "tom1");

        ArrayList<Map.Entry> entries = new ArrayList<Map.Entry>();

        //准备数据
        Map.Entry root = getRoot(map);
        Map.Entry left = getEntry(root, "left");
        Map.Entry right = getEntry(root, "right");

        //填充数据
        entries.add(left);
        entries.add(right);

        //遍历元素
        System.out.println(root.getKey());
        iter(entries);
    }
    /**
     * 层序： 遍历
     * //1级:  left , right
     */
    static void iter(List<Map.Entry> list ) throws Exception {

        if (list.size() ==0)return;
        ArrayList<Map.Entry> list2 = new ArrayList<Map.Entry>();

        for (int i=0;i<list.size();i++) {

            Map.Entry entry=list.get(i);

            //按顺序： 从左到右--》遍历key
            if (entry !=null ) {
                System.out.println(entry.getKey() + ", ");

                list2.add(getEntry(entry, "left"));
                list2.add(getEntry(entry, "right"));
            }
        }

        iter(list2);
    }
    //-----------------------------
    /**
     * 工具方法
     * @throws Exception
     */
    public static Map.Entry getRoot(TreeMap map) throws Exception {
        Field f = TreeMap.class.getDeclaredField("root") ;
        f.setAccessible(true);
        Object o = f.get(map) ;
        return (Map.Entry) o;
    }
    public static Map.Entry getEntry(Map.Entry e, String location) throws Exception {
        // left, right
        Field f = e.getClass().getDeclaredField(location) ;
        f.setAccessible(true);
        Object o = f.get(e) ;
        return (Map.Entry) o;
    }
}
