package db;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbBean implements Writable,DBWritable {
    //属性
    private int id;
    private String name;

    //set,get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //构造


    public DbBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DbBean() {
    }

    //序列化
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(name);
    }

    public void readFields(DataInput in) throws IOException {
        id=in.readInt();
        name=in.readUTF();
    }


    //db
    public void write(PreparedStatement statement) throws SQLException {
        statement.setInt(1,id);
        statement.setString(2,name);
    }

    public void readFields(ResultSet resultSet) throws SQLException {
       id= resultSet.getInt(1);
        name=  resultSet.getString(2);
    }
}
