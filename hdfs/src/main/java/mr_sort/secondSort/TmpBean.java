package mr_sort.secondSort;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TmpBean implements WritableComparable{
    //属性
    private String year;
    private int tmp;

    //构造
    public TmpBean(String year, int tmp) {
        this.year = year;
        this.tmp = tmp;
    }

    public TmpBean() {
    }

    //set, get

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    ////////////////////
    public int compareTo(Object o) {
        //先比year    再比tmp
        if(o instanceof TmpBean){
            TmpBean t1 = (TmpBean) o;

            if(this.year.equals(t1.year)){
                return -(this.tmp- t1.getTmp());
            }
            return -(this.year.compareTo(t1.year));
        }
        return  0;
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(year);
        out.writeInt(tmp);
    }

    public void readFields(DataInput in) throws IOException {
        year=in.readUTF();
        tmp=in.readInt();
    }
}
