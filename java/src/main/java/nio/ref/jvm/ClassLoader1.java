package nio.ref.jvm;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ClassLoader1 extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {


        //加载class文件
        try {
            FileInputStream in = new FileInputStream("/home/centos/Student.class");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();


            byte[] buf = new byte[1024 ];
            int len = in.read(buf);
            while (len!=-1){
                baos.write(buf, 0,len);
                len=in.read(buf);
            }
            in.close();
            baos.close();

            // defineClass()
            byte[] data=baos.toByteArray();
            Class<?> clazz = defineClass(data, 0, data.length);
            return clazz;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Test
    public void t() throws ClassNotFoundException {

        Class<?> clz = new ClassLoader1().loadClass("");

        System.out.println();
    }
}
