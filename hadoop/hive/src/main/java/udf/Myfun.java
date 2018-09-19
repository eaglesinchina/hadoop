package udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;


@Description(
        name="myfun",
        value="return a+b",
        extended = "..."
)
public class Myfun extends UDF {

    public int evaluate(int a, int b){
        return a+b;
    }
    public String evaluate(String a, String b){
        return a+b;
    }


}
