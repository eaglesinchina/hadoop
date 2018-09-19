package json.mpr2;


import codec.Comp;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ComparKey implements WritableComparable<ComparKey> {

    //属性
    private int count;
    private String content;


    //构造
    public ComparKey( int count, String content) {
        this.count = count;
        this.content = content;
    }

    public ComparKey() {
    }

    //set,get
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

////////////


    public int compareTo(ComparKey o) {
        ComparKey c = (ComparKey)o;
        int res1=-(this.count- c.count);
        int res2=-(this.content.compareTo(c.content));

        return( res1==0?res2:res1);
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(count);
        out.writeUTF(content);
    }

    public void readFields(DataInput in) throws IOException {
        count=in.readInt();
        content=in.readUTF();
    }
}
