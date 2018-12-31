package ser;

import myavro.User;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserWritable implements Writable {
    //持有对象
    private  User user;

    public UserWritable(User user) {
        this.user = user;
    }


    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(user.getId());
        out.writeUTF(user.getName().toString());
        out.writeUTF(user.getAddr().toString());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        user.setId(in.readInt());
        user.setName(in.readUTF());
        user.setAddr(in.readUTF());

    }
}
