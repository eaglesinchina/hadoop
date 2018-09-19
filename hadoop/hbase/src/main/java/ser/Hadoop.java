package ser;

import a.b.User;

import java.io.*;

public class Hadoop {

    public static void main(String[] args) throws  Exception {
        FileOutputStream out = new FileOutputStream("/home/centos/ser-time.txt",true);
        long start=System.currentTimeMillis();

        //序列化
        ser();
        long t1=System.currentTimeMillis();
        out.write(("hadoop ser 结束时间--->"+(t1-start)  ).getBytes());
        //反序列化
        deser();
        out.write(("hadoop deser 结束时间--->"+(System.currentTimeMillis()-t1)  ).getBytes());
        out.write(("hadoop ser txt  大小：--->"+(new File("/home/centos/hadoop-ser.txt").length())  ).getBytes());
        out.write(("hadoop deser txt  大小：--->"+(new File("/home/centos/hadoop-deser.txt").length())  ).getBytes());
        out.close();
    }


    public static void ser() throws Exception {


        for(int i=1;i<=1000000;i++){
            User user=new User();
            UserWritable uw = new UserWritable(user);
            user.setId(i);
            user.setName("tom"+i);
            user.setAddr("changping"+i);

            //流.write
            uw.write(new DataOutputStream(new FileOutputStream("/home/centos/hadoop-ser.txt")));
        }

    }

    public static void deser() throws Exception {

        FileOutputStream out = new FileOutputStream("/home/centos/hadoop-deser.txt");
        long start=System.currentTimeMillis();

        for(int i=1;i<=1000000;i++){
            User user=new User();
            UserWritable uw = new UserWritable(user);
            uw.readFields(new DataInputStream(new FileInputStream("/home/centos/hadoop-ser.txt")));

            //流.write
            out.write(user.getId());
            out.write(user.getName().toString().getBytes());
            out.write(user.getAddr().toString().getBytes());

        }

        out.close();
    }
}
