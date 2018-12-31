package udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class MyUDTF2 extends GenericUDTF {
    PrimitiveObjectInspector oi;
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {

         oi = (PrimitiveObjectInspector) argOIs.getAllStructFieldRefs().get(0).getFieldObjectInspector();

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<ObjectInspector>  fieldtypes = new ArrayList<ObjectInspector>();
        //定义： 类型
        names.add("str");
        names.add("count");
        //定义：字段
        fieldtypes.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldtypes.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(names,fieldtypes);
    }

    public void process(Object[] args) throws HiveException {
        //传入数据： select f('a,a,a,b,c')---->解析： wordcount( a,3)
        //                                                  (b,1)
        //                                                   (c,1)
        System.out.println("参数args[0]"+args[0]);
        String obj = (String)oi.getPrimitiveJavaObject(args[0]);

        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String[] strs = obj.split(",");

        for (String arg: strs) {
            System.out.println("对象检查器--get: "+arg);

            if(map.containsKey(arg)){
                map.put(arg ,Integer.valueOf(map.get(arg)+1 )  );
            }else {
                map.put(arg, 1);
            }
        }

        //发送数据
        for (String key : map.keySet()) {

            forward(new Object[]{key, map.get(key)});
            System.out.println("forward--"+key+","+map.get(key));
        }
    }

    public void close() throws HiveException {

    }
}
