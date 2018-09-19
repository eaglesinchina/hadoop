package avro;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestAvro {

    public static void main(String[] args) throws IOException {
        //调用writer对象
        DatumWriter dw = new SpecificDatumWriter<User>(User.class);

        DataFileWriter<User> dfw = new DataFileWriter<User>(dw);
        long start=System.currentTimeMillis();

        dfw.create(User.SCHEMA$, new File("user.avro"));
        for (int i=1;i<=1000000;i++) {

            //创建对象
            User u = new User();
            u.setAddr("beijin");
            u.setId(1);
            u.setName("lisi");

            dfw.append(u);
        }
        //关闭资源
        dfw.close();
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public void deser() throws IOException {

        //创建流
        DatumReader<User> read =new SpecificDatumReader<User>();
        DataFileReader<User> dfr = new DataFileReader<User>(new File("user.avro2"), read);

        //
        User user = new User();

        while (dfr.hasNext()){
            User u = dfr.next(user);
            System.out.println(u.getName());
            System.out.println(u.getAddr());

        }
    }

    @Test
    public void testnon_obj_deser() throws IOException {

        //创建schema
        Schema schema = new Schema.Parser().parse(new File("user.avsc"));
        //创建流
        DatumWriter dw=new SpecificDatumWriter(User.class);

        DataFileWriter dfw=new DataFileWriter(dw);
        dfw.create(schema,new File("user.avro2"));


        for (int i=1;i<10;i++){
            User user = new User();

            user.setAddr("beijing");
            user.setName("lisi"+i);
            dfw.append(user);
        }

         //关闭资源
        dfw.close();
    }
}
