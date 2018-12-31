package logsETL.receive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.util.ArrayList;

public class UDF1 extends GenericUDTF {
    PrimitiveObjectInspector po;


    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        ObjectInspector fieldObjectInspector = argOIs.getAllStructFieldRefs().get(0).getFieldObjectInspector();

        if(! fieldObjectInspector.getCategory().equals(ObjectInspector.Category.PRIMITIVE)){
            throw new UDFArgumentException("......");
        }

         po = (PrimitiveObjectInspector) fieldObjectInspector;
        //对象检查器----》字段： 类型

        ArrayList<String> names = new ArrayList<>();
        ArrayList<ObjectInspector> fields = new ArrayList<>();

        for(int i=0;i<10;i++){
            names.add("col"+(i+1) );
            fields.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        }


        System.out.println("init方法...");
        return ObjectInspectorFactory.getStandardStructObjectInspector(names, fields);
    }

    /**
     * 字段数   >   forward数
     * @param args
     * @throws HiveException
     */


    @Override
    public void process(Object[] args) throws HiveException {
        String line = (String) po.getPrimitiveJavaObject(args[0]);

        //数据： 加工后转发
        String[] splits = line.split(",");

        if(splits.length <10){

            int len=10-splits.length;
            ArrayList<String> list = new ArrayList<>();

            for (String s : splits) {
                list.add(s);
            }

            for (int i=0;i<len;i++){
                list.add("null");
                System.out.println("添加null---->"+ i);
            }

            splits= new String[list.size()];
            for (int i=0;i<list.size();i++) {
                splits[i]=list.get(i);
            }
        }
        forward(splits);

    }

    @Override
    public void close() throws HiveException {

    }
}
