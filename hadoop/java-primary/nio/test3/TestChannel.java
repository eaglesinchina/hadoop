package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    public static void main(String[] args) throws IOException {
        //文件拷贝
                //1，输入流＋　通道
        FileInputStream in = new FileInputStream("/home/centos/1.txt");
        FileChannel chin = in.getChannel();


        //2，输出流　＋通道
        FileOutputStream out = new FileOutputStream("/home/centos/111.txt");
        FileChannel chout = out.getChannel();

                //3, 缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
                //4, 通道：　读写

        while(chin.read(buf) !=0){
            buf.flip();//抛　出
            chout.write(buf);
        }

        //5. 资源关闭
        chout.close();
        out.close();
        chin.close();
        in.close();
    }
}
