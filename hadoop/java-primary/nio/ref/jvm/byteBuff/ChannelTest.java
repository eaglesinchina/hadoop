package ref.jvm.byteBuff;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelTest {

    public static void main(String[] args) throws Exception {

        /**
         * 文件流----->获取channel
         */
        FileChannel outchannel = new FileOutputStream("").getChannel();
        FileChannel channel1 = new FileOutputStream("").getChannel();

        ByteBuffer buf = ByteBuffer.allocate(12312);

        buf.flip();
        outchannel.write(buf);


        /**
         * socket----->获取channel
         */
        ServerSocketChannel serchannel = new ServerSocket(8888).getChannel();
        SocketChannel channel = new Socket("", 8888).getChannel();

        /**
         * Channel实现类： ---》获取channel
         */
        ServerSocketChannel serchannel2 = ServerSocketChannel.open();
        SocketChannel channel2 = SocketChannel.open();
    }
}
