package myhive;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(
        name="name:  myfunction",
        value="function:  return  a+b ",
        extended="int , string --->return a+b"
)
public class Myfun  extends UDF {

    public int evaluate(int a, int b){
        return a+b;

    }

    public String evaluate(String a, String b){
        return a+b;

    }
}
