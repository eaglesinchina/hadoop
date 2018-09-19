package ser;

import a.b.User;

import java.io.*;

public class Java {

    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("/home/centos/ser-time.txt",true);
        long start=System.currentTimeMillis();

        //序列化
        ser();
        long t1=System.currentTimeMillis();
        out.write(("java ser 结束时间--->"+(t1-start)  ).getBytes());
        //反序列化
        deser();
        out.write(("java ser 结束时间--->"+(System.currentTimeMillis()-t1)  ).getBytes());

        out.write(("java ser txt  大小：--->"+(new File("/home/centos/java-ser.txt").length())  ).getBytes());
        out.write(("java deser txt  大小：--->"+(new File("/home/centos/java-deser.txt").length())  ).getBytes());
        out.close();
        out.close();
    }

    public  static void ser() throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/home/centos/java-ser.txt"));
      //  out.write(("ser 开始时间--->"+System.currentTimeMillis()).getBytes());

        for (int i=1;i<=1000000;i++){
            User user=new User();
            //流.write(对象)
            user.setName("tom"+i);
            user.setId(i);
            user.setAddr("changpin"+i);

            out.writeObject(user);
        }

        //关闭
        out.close();
       // out.write(("ser  结束时间--->"+System.currentTimeMillis()).getBytes());
    }

    public static void deser() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/centos/java-ser.txt"));

        FileOutputStream out = new FileOutputStream("/home/centos/java-deser.txt");
        long start=System.currentTimeMillis();

        for (int i=1;i<=1000000;i++){
            User user=new User();
            //流.write(对象)
            user = (User) in.readObject();
            out.write(user.getId());
            out.write(user.getName().toString().getBytes());
            out.write(user.getAddr().toString().getBytes());
        }

        //关闭
        in.close();
        out.close();
    }
}
