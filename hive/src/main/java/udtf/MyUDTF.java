package udtf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;

import java.util.HashMap;

public class MyUDTF extends GenericUDTF {
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        return super.initialize(argOIs);
    }

    public void process(Object[] args) throws HiveException {
        //传入数据： select f('a,a,a,b,c')---->解析： wordcount( a,3)
        //                                                  (b,1)
        //                                                  (c,1)
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (Object arg : args) {
            System.out.println("输入的元素"+arg);

            if(map.containsKey(arg)){
                map.put(arg.toString() ,Integer.valueOf(map.get(arg)+1 )  );
            }
        }

        //发送数据
        for (String key : map.keySet()) {

            forward(new Object[]{key, map.get(key)});
        }
    }

    public void close() throws HiveException {

    }
}
