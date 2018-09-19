package reflect.utils;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyUDTF extends GenericUDTF {
    private PrimitiveObjectInspector stringOI = null;

    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {

        //获取对象检查器
        List<? extends StructField> allStructFieldRefs = argOIs.getAllStructFieldRefs();

       stringOI   = (PrimitiveObjectInspector) allStructFieldRefs.get(0).getFieldObjectInspector();
        if (allStructFieldRefs.size() != 1) {
            throw new UDFArgumentException("needs  only one argument");
        }

        if (stringOI.getCategory() != ObjectInspector.Category.PRIMITIVE
                && stringOI.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING) {
            throw new UDFArgumentException("need  a string  parameter....");
        }

        // 输出格式（inspectors） -- 有两个属性的对象
        List<String> fieldNames = new ArrayList<String>();
        List<ObjectInspector> fieldOIs = new ArrayList<ObjectInspector>();
        fieldNames.add("c1");
        fieldNames.add("c2");
        fieldNames.add("c3");
        fieldNames.add("c4");
        fieldNames.add("c5");
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
    }


    @Override
    public void process(Object[] record) throws HiveException {

        final String name = stringOI.getPrimitiveJavaObject(record[0]).toString();

        // 忽略null值与空值
        if (name == null || name.isEmpty()) {
            return ;
        }

        String[] fields = name.split("#");
        for (String field : fields) {

            forward(new Object[] {
                    fields[0], fields[1] ,fields[2],fields[3],fields[4]
            } );
        }
    }

    @Override
    public void close() throws HiveException {

    }
}
