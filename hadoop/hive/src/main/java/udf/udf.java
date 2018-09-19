package udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

public class udf extends UDF {

//    public  int evaluate(int a) {
//        return a+1;
//
//    }

    public  String evaluate(String a) {
        return a+"hello";

    }
    
}
