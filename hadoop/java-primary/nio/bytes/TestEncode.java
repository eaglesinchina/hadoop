package bytes;

import org.junit.Test;

import java.io.*;

public class TestEncode {

    /**
     * 字节---字符编码
     */
    
    @Test
    public void test() throws UnsupportedEncodingException {
        String a="也是";
        byte[] unicodes = a.getBytes("unicode");

        String unicode = new String(unicodes, "unicode");
        System.out.println(unicode);
    }

    @Test
    public void t2() throws IOException, ClassNotFoundException {
        /**
         * 对象：序列化：－－》对象深度复制？（shux非引用）
         */
        A a = new A();
        a.b=new B("aa");

        //ser
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream objout = new ObjectOutputStream(out);
        objout.writeObject(a);

        objout.close();
        out.close();
        byte[] bytes=out.toByteArray();

        //deser
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        A o = (A)new ObjectInputStream(in).readObject();

        A a2=o;
        System.out.println(a2.b.name);
        a2.b.name="lisi";

        System.out.println(a2.b.name+","+a.b.name);
    }
}
class A implements Serializable{
    B b;
}
class B implements Serializable{
    String name;

    public B(String name) {
        this.name = name;
    }
}