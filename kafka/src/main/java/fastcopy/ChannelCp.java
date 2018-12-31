package fastcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class ChannelCp {
    public static void main(String[] args)throws Exception {

        //输入流
        File file = new File("/home/centos/logs2.zip");
        FileInputStream in = new FileInputStream(file);

        //输出流
        FileOutputStream out = new FileOutputStream("/home/centos/logs3.zip");
        //拷贝

        FileChannel chin = in.getChannel();
        FileChannel chout = out.getChannel();

        long start=System.currentTimeMillis();
        chin.transferTo(0,file.length(), chout);



        //关闭资源
        chin.close();
        chout.close();

        in.close();
        out.close();

        System.out.println("channel-copy-->"+(System.currentTimeMillis()-start));
    }
}
