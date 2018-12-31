package treemap;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TestTreemap2 {

    //得到e节点的k值
    public static Object getKey(Map.Entry e) throws Exception {
        Field f = e.getClass().getDeclaredField("key") ;
        f.setAccessible(true);
        Object o = f.get(e) ;
        return o;
    }

    public static Map.Entry getLeft(Map.Entry e) throws Exception {
        Field f = e.getClass().getDeclaredField("left") ;
        f.setAccessible(true);
        Object o = f.get(e) ;
        return (Map.Entry) o;
    }

    public static Object getLeftKey(Map.Entry e) throws Exception {

        return  e.getKey();
//        Field f = e.getClass().getDeclaredField("left") ;
//        f.setAccessible(true);
//        Object o = f.get(e) ;
//        if(o != null){
//            Field k = o.getClass().getDeclaredField("key") ;
//            k.setAccessible(true);
//            return k.get(o) ;
//        }
//        return null;
    }

    public static Map.Entry getRight(Map.Entry e) throws Exception {
        Field f = e.getClass().getDeclaredField("right") ;
        f.setAccessible(true);
        Object o = f.get(e) ;
        return (Map.Entry) o;
    }

    public static Object getRightKey(Map.Entry e) throws Exception {
        return  e.getKey();
//        Field f = e.getClass().getDeclaredField("right");
//        f.setAccessible(true);
//        Object o = f.get(e);
//        if (o != null) {
//            Field k = o.getClass().getDeclaredField("key");
//            k.setAccessible(true);
//            return k.get(o);
//        }
//        return null;
    }

    /**
     * 前序遍历： 根左右
     * @param e
     * @throws Exception
     */
//------------------
    public static void preOrderTravel(Map.Entry e) throws Exception {
        if(e != null){
            System.out.println(getKey(e));
            preOrderTravel(getLeft(e)) ;
            preOrderTravel(getRight(e)) ;
        }
    }


    /**
     * 中序遍历：left 根 右
     * @param e
     * @throws Exception
     */
//------------------
    public static void preOrderTravelLeft(Map.Entry e) throws Exception {

        if(e != null){

            preOrderTravelLeft(getLeft(e)) ;

            System.out.println(getKey(e));

            preOrderTravelLeft(getRight(e)) ;
        }
    }
    //------------------
    public static void preOrderTravelRight(Map.Entry e) throws Exception {

        if(e != null){

            preOrderTravelRight(getLeft(e)) ;

            preOrderTravelRight(getRight(e)) ;

            System.out.println(getKey(e));
        }
    }

//------------------

    /**
     * 层序遍历
     *
     * @param e
     * @throws Exception
     */
    public static void preOrderTravelLevel(Map.Entry e) throws Exception {

        ArrayList<Map.Entry> list = new ArrayList<Map.Entry>();
        list.add(e);
        System.out.println(getKey(e));

        list.remove(e);
        list.add(getLeft(e));
        list.add(getRight(e));



        if(e != null){

        /**
         * 左--->---右
         */
        Map.Entry left = getLeft(e);
        if(e.getKey() !=null){

            System.out.println(getKey(e));
        }
        Map.Entry right = getRight(e);
    }
}


    //------------------
    public static Map.Entry getRoot(TreeMap map) throws Exception {
        Field f = TreeMap.class.getDeclaredField("root") ;
        f.setAccessible(true);
        Object o = f.get(map) ;
        return (Map.Entry) o;
    }




    @Test
    public void testNewTree2() throws Exception {
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        map.put(1 , "tom1");
        map.put(2 , "tom1");
        map.put(3 , "tom1");
        map.put(4 , "tom1");
        map.put(5 , "tom1");

        map.put(6 , "tom1");
        map.put(7 , "tom1");
        map.put(8 , "tom1");
        map.put(9 , "tom1");

        preOrderTravel(getRoot(map));
//        preOrderTravelLeft(getRoot(map));
//        preOrderTravelRight(getRoot(map));
    }


}
