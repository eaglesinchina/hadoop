package nio.nio3.nio2;

import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ser {

    public static void main(String[] args) throws IOException, InterruptedException {

        //启动： 服务器端channel
        ServerSocketChannel serchannel = ServerSocketChannel.open();
        serchannel.configureBlocking(false);
        InetSocketAddress addr = new InetSocketAddress("0.0.0.0", 8888);
        serchannel.bind(addr);

        Selector selector = Selector.open();
        serchannel.register(selector, SelectionKey.OP_ACCEPT);

        int i = 1;
        ByteBuffer buf = ByteBuffer.allocate(1024);

        //死循环： 挑选key
        while (true) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> it = keys.iterator();
            selector.select();

            while (it.hasNext()) {
                SelectionKey key = it.next();

                try {

                    if (key.isAcceptable()) {
                        System.out.println("accetable...");
                        SocketChannel socketChannel = serchannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT);
                    }

                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        buf.clear();
                        socketChannel.read(buf);
                        buf.flip();

                        System.out.println("server: 接收消息---" + new String(buf.array(), 0, buf.limit()));
                    }

                    if (key.isWritable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        System.out.println("server: 发送消息---: tom" + (i++));
                        socketChannel.write(ByteBuffer.wrap(("tom" + (i++)).getBytes("utf-8")));

                        Thread.sleep(500);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //删除key
                it.remove();
            }

        }

    }
}
