package nio.ref.jvm.byteBuff;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile {

    /**
     * 拷贝： 2g 文件---》 byteBuff, DirectBytebuff
     */
    public long t() throws Exception {
        //创建缓存对象
        long start = System.nanoTime();
        ByteBuffer buf = ByteBuffer.allocate(1024 * 1024);
        //文件拷贝

        FileInputStream in = new FileInputStream("/home/centos/pack/CentOS-7-x86_64-Minimal-1804.iso");
        FileChannel channel = in.getChannel();
        FileOutputStream out = new FileOutputStream("/home/centos/a.iso");
        FileChannel outchannel = out.getChannel();

        int len = 0;
        while ((len = channel.read(buf)) != -1) {
            buf.flip();
            outchannel.write(buf);
            buf.flip();
        }

        //关闭资源
        out.close();
        in.close();
        outchannel.close();
        channel.close();
        long time = System.nanoTime() - start;
        System.out.println("time=" + (time));
        return time;

    }

    public long t2() throws Exception {
        //创建缓存对象
        long start = System.nanoTime();
        ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 1024);
        //文件拷贝

        FileInputStream in = new FileInputStream("/home/centos/pack/CentOS-7-x86_64-Minimal-1804.iso");
        FileChannel channel = in.getChannel();
        FileOutputStream out = new FileOutputStream("/home/centos/aa.iso");
        FileChannel outChannel = out.getChannel();

        int len = 0;
        while ((len = channel.read(buf)) != -1) {
            buf.flip();
            outChannel.write(buf);
            buf.flip();
        }

        //关闭资源
        out.close();
        in.close();
        outChannel.close();
        long time = System.nanoTime() - start;
        System.out.println("time=" + (time));
        return time;
    }


    /**
     * 缓冲流： copy文件
     * @throws Exception
     */
    public void tInputOutput() throws IOException {
        long start=System.nanoTime();
        
        BufferedInputStream bfin = new BufferedInputStream(new FileInputStream("/home/centos/pack/CentOS-7-x86_64-Minimal-1804.iso"));
        byte[] buf = new byte[1024 * 1024];
        int len=0;

        BufferedOutputStream bfout= new BufferedOutputStream(new FileOutputStream("/home/centos/a3.iso"));

        while ((len=bfin.read(buf))!=-1){

                bfout.write(buf,0, len);
        }
        //关闭资源
        bfout.close();
        bfin.close();
        long time=System.nanoTime()-start;
        System.out.println("time="+time);

    }

    /**
     * sendfile: 文件copy
     * @throws Exception
     */

    public void sendFileCopyt() throws IOException {
        long start=System.nanoTime();

        File file = new File("/home/centos/pack/CentOS-7-x86_64-Minimal-1804.iso");
        FileChannel channelout = new FileOutputStream("/home/centos/a-sendf.iso").getChannel();
        FileChannel channelin = new FileInputStream(file).getChannel();

        long len = channelin.transferTo(0, file.length(), channelout);
        System.out.println("res="+len);

        long time=System.nanoTime()-start;
        System.out.println("time="+time);
    }

    @Test
    public void t3() throws Exception {
//        long t2 = t2();
//        long t1 = t();
//        tInputOutput();
        sendFileCopyt();
//        System.out.println( (t2-t1) );

    }

}