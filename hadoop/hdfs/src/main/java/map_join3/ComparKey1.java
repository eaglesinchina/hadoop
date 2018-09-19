package map_join3;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ComparKey1 implements WritableComparable<ComparKey1> {
    //属性
    private int flag;
    private String userid;

    //构造


    public ComparKey1(int flag, String userid) {
        this.flag = flag;
        this.userid = userid;
    }

    public ComparKey1() {
    }

    //set,get
    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    //==================

    public int compareTo(ComparKey1 o) {
        //使得： cus, ord 进入一个分区，  userid相同--》一个reduce
        if(this.userid.equals(o.getUserid())){
            return this.flag-o.getFlag();
        }else {
            return this.getUserid().compareTo(o.getUserid());
        }

    }

    public void write(DataOutput out) throws IOException {

    }

    public void readFields(DataInput in) throws IOException {

    }
}
