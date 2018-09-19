package qq.base.util;

import qq.base.ServerFreshFriends;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.List;

public class QQutils {

    public static String getRemotAddrrHostname(SocketChannel socketChannel) throws Exception {

        Socket socket = socketChannel.socket();
        InetSocketAddress addr = (InetSocketAddress) socket.getRemoteSocketAddress();
        int port = addr.getPort();
        String hostName = addr.getAddress().getHostAddress();

        return  hostName+":"+port;
    }
    public static String getLocalAddrrHostname(Socket socket) throws Exception {

        InetAddress addr = socket.getLocalAddress();
        String hostName = addr.getHostAddress();
        int port = socket.getLocalPort();

        return  hostName+":"+port;
    }

    //序列号： 好友
    public static byte[] serFriends(List<String> obj) throws IOException {
        //存入： 内存
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bao);

        out.writeObject(obj);
        out.close();
        bao.close();

        System.out.println("list<string>===>"+bao.toByteArray().length);
        return  bao.toByteArray();
    }
    //序列号： 好友
    public static List<String>  deserFriends(byte[] bytes) throws IOException, ClassNotFoundException {
        //存入： 内存
        ByteArrayInputStream byi = new ByteArrayInputStream(bytes);
        System.out.println("descFriends--bytes[len]="+bytes.length);

        ObjectInputStream in = new ObjectInputStream(byi);
        List<String> list= (List<String>) in.readObject();

        byi.close();
        in.close();

        return  list;
    }
}
