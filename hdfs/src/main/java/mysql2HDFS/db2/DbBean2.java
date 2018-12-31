package mysql2HDFS.db2;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBean2 implements Writable,DBWritable {
    //属性
    private int id;
    private String word;
    private int count;

    //set,get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //构造
    public DbBean2( int id,String word,int count) {
        this.id=id;
        this.word = word;
        this.count=count;
    }

    public DbBean2() {
    }

    //序列化
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(word);
        out.writeInt(count);
    }

    public void readFields(DataInput in) throws IOException {
        id=in.readInt();
        word=in.readUTF();
        count=in.readInt();
    }


    //mysql2HDFS
    public void write(PreparedStatement statement) throws SQLException {
        statement.setInt(1,id);
        statement.setString(2,word);
        statement.setInt(3,count);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
       id= resultSet.getInt(1);
        word=  resultSet.getString(2);
        count=resultSet.getInt(3);
    }
}
