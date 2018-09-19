package reflect.receive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.List;

public class MyUDTF3 extends GenericUDTF {
    private PrimitiveObjectInspector stringOI = null;
    //选择： 字段类型
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //获取对象检查器
        List<? extends StructField> allStructFieldRefs = argOIs.getAllStructFieldRefs();
        stringOI   = (PrimitiveObjectInspector) allStructFieldRefs.get(0).getFieldObjectInspector();


        // 输出格式（inspectors） -- 有两个属性的对象
        List<String> fieldNames = null;
        try {
            fieldNames = GetKey_tab_name.getMap_tbname_prop2("appBaseLog");
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();

       // for(int i=0;i<fieldNames.size();i++){
        for(int i=0;i<15;i++){

            fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }

    //解析f( 参数）
    @Override
    public void process(Object[] args) throws HiveException {
        String json = (String)  stringOI. getPrimitiveJavaObject(args[0]);
        String tbname = (String)  stringOI. getPrimitiveJavaObject(args[1]);

        try {
            List<String> list = ParseJson.splitTabs1(json, tbname);

            forward(list.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }








    @Override
    public void close() throws HiveException {

    }
}
