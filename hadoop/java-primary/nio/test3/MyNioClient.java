package test3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 客户端
 */
public class MyNioClient {
	public static void main(String[] args) throws Exception {
		//连接到远程服务器
		SocketChannel sc = SocketChannel.open();
		InetSocketAddress srvAddr = new InetSocketAddress("localhost" , 8888) ;
		sc.connect(srvAddr) ;
		sc.configureBlocking(false);
		//挑选器
		Selector sel = Selector.open();
		SelectionKey key = sc.register(sel , SelectionKey.OP_WRITE | SelectionKey.OP_READ | SelectionKey.OP_CONNECT) ;

		int index = 1 ;
		while(true){
			sel.select() ;

			if(key.isConnectable()){
				System.out.println("可连接!-------------");
				sc.finishConnect();
				System.out.println("连接完了!--------------");
			}

			if(key.isWritable()){
				System.out.println("可写");
				String msg = "aa" + index ;
				ByteBuffer buf = ByteBuffer.wrap(msg.getBytes()) ;
				//发送消息给服务器
				sc.write(buf) ;
				index ++ ;
				Thread.sleep(200);
				System.out.println("写完了...");
			}

			//是否可读
			if(key.isReadable()){
				SocketChannel sc0 = (SocketChannel) key.channel();
				System.out.println(MyNioServer.getRemoteAddr(sc0) + " 可读了");
				String msg = MyNioServer.readStringFromChannel(sc0) ;
				System.out.println(MyNioServer.getRemoteAddr(sc0) + " 发来消息: " + msg);
			}

			//清空挑选集合
			sel.selectedKeys().clear();
			Thread.sleep(1000);
		}
	}
}
