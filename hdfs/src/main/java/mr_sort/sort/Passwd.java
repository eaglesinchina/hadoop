package mr_sort.sort;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Passwd extends WritableComparator implements Writable {
    //属性
    private String pwd;
    private  int count;

    //构造
    public Passwd(String pwd, int count){
        this.pwd=pwd;
        this.count=count;
    }
    public Passwd(){
    }



    //set, get
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void write(DataOutput out) throws IOException {
            out.writeUTF(pwd);
            out.writeInt(count);
    }

    public void readFields(DataInput in) throws IOException {
        pwd = in.readUTF();
         count = in.readInt();
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Passwd a1 = (Passwd) a;
        Passwd b1 = (Passwd) b;

        return b1.getCount()-a1.getCount();
    }
}
