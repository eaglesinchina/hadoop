package nio.ref.jvm.byteBuff;

import javafx.scene.input.TouchEvent;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ZeroCopy {

    @Test
    public void t() throws IOException {
        long start=System.currentTimeMillis();
        //输入， 输出
        File file = new File("/home/centos/xp6.iso");
        FileInputStream in = new FileInputStream(file);
        FileChannel inchannal = in.getChannel();

        FileOutputStream out = new FileOutputStream("/home/centos/xp7.iso");
        FileChannel outchannel = out.getChannel();

        long len = inchannal.transferTo(0, file.length(), outchannel);

        //关闭资源
        outchannel.close();
        out.close();
        inchannal.close();
        in.close();

        System.out.println(len+",  time="+(System.currentTimeMillis()-start));
    }

    @Test
    public void t2() throws IOException {
        long start=System.currentTimeMillis();
        //输入， 输出
        File file = new File("/home/centos/xp.iso");
        FileInputStream in = new FileInputStream(file);

        FileOutputStream out = new FileOutputStream("/home/centos/xp2.iso");
        byte[] buf = new byte[1024];
        int len=0;

        while ((len=in.read(buf))!=-1){
            out.write(buf, 0, len);
        }

        //关闭资源
        out.close();
        in.close();

        System.out.println(len+",  time="+(System.currentTimeMillis()-start));
    }

    @Test
    public void t3() throws IOException {
        long start=System.currentTimeMillis();
        //输入， 输出
        File file = new File("/home/centos/xp2.iso");
        FileInputStream in = new FileInputStream(file);
        BufferedInputStream bfin = new BufferedInputStream(in);

        FileOutputStream out = new FileOutputStream("/home/centos/xp3.iso");
        BufferedOutputStream bfout = new BufferedOutputStream(out);

        byte[] buf = new byte[1024];
        int len=0;

        while ((len=bfin.read(buf))!=-1){
            bfout.write(buf, 0, len);
        }

        //关闭资源
        bfout.close();
        out.close();
        bfin.close();
        in.close();

        System.out.println(len+",  time="+(System.currentTimeMillis()-start));
    }

    /**
     * channel -->bytebuff
     * @throws IOException
     */
    @Test
    public void t4() throws IOException {
        long start=System.currentTimeMillis();
        //输入， 输出
        File file = new File("/home/centos/xp3.iso");
        FileInputStream in = new FileInputStream(file);
        FileChannel inchannel = in.getChannel();

        FileOutputStream out = new FileOutputStream("/home/centos/xp4.iso");
        FileChannel outchannel = out.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);

        while ( (inchannel.read(buf))!=-1){
            buf.flip();
            outchannel.write(buf);
            buf.flip();
        }

        //关闭资源
        outchannel.close();
        out.close();
        inchannel.close();
        in.close();

        System.out.println(",  time="+(System.currentTimeMillis()-start));
    }
    /**
     * channel -->bytebuff--direct
     * @throws IOException
     */
    @Test
    public void t5() throws IOException {
        long start=System.currentTimeMillis();
        //输入， 输出
        File file = new File("/home/centos/xp4.iso");
        FileInputStream in = new FileInputStream(file);
        FileChannel inchannel = in.getChannel();

        FileOutputStream out = new FileOutputStream("/home/centos/xp5.iso");
        FileChannel outchannel = out.getChannel();

        ByteBuffer buf = ByteBuffer.allocateDirect(1024);

        while ( (inchannel.read(buf))!=-1){
            buf.flip();
            outchannel.write(buf);
            buf.flip();
        }

        //关闭资源
        outchannel.close();
        out.close();
        inchannel.close();
        in.close();

        System.out.println(",  time="+(System.currentTimeMillis()-start));
    }
}
