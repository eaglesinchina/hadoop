package qq.base2.bussiness.ser;

import qq.base2.BaseMessage;
import qq.base2.ServerFreshFriends;
import qq.base2.util.Constants;
import qq.base2.util.QQutils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class QQserver {
    //启动线程池：  接收消息---》转发
    ExecutorService threadpool;

    //构造： 好友列表---单例模式
    private QQserver() {
    }

    private static QQserver obj;

    public static QQserver getInstance() {
        if (obj != null) return obj;
        synchronized (QQserver.class) {
            if (obj == null) {
                obj = new QQserver();
            }
            return obj;
        }
    }

    Map<String, SocketChannel> friends = new HashMap<String, SocketChannel>();
    public List<String> getFriends(){
        return new ArrayList<>(friends.keySet());
    }

    public  byte[] getFriendsBytes() throws IOException {
        System.out.println("serfriends:  getfriends--->0: "+new ArrayList<>(friends.keySet()).get(0));
        ArrayList<String> list = new ArrayList<>(friends.keySet());

        return QQutils.serFriends(list);
    }

    //======启动服务======
    public void start() throws Exception {
        //threadpool线程池
        threadpool = Executors.newFixedThreadPool(Constants.poolSizeValue);

        //socket通信
        ServerSocketChannel serchannel = ServerSocketChannel.open();
        serchannel.configureBlocking(Boolean.parseBoolean(Constants.serverBlookModeValue));

        InetSocketAddress addr = new InetSocketAddress(Constants.serverIPValue, Constants.serverPortValue);
        serchannel.bind(addr);

        Selector selector = Selector.open();
        serchannel.register(selector, SelectionKey.OP_ACCEPT);


        //死循环： 挑选Key,  遍历key--判断key类型
        while (true) {

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            selector.select();
            while (it.hasNext()) {
                SelectionKey key = it.next();

                try {
                    //获取并注册： channel
                    if (key.isAcceptable()) {
                        //注册channel
                        SocketChannel   socketChannel = serchannel.accept();
                        socketChannel.configureBlocking(Boolean.parseBoolean(Constants.serverBlookModeValue));
                        SelectionKey key0 = socketChannel.register(selector, SelectionKey.OP_READ);

                        ReentrantLock lock = new ReentrantLock();
                        key0.attach(lock);

                        //添加好友=======
                        friends.put(QQutils.getRemotAddrrHostname(socketChannel), socketChannel);

                        //广播： 好友
                       forwardFriends(socketChannel);
                        System.out.println("已广播friends...");
                    }

                    //接收 发送的消息
                    if (key.isReadable()) {
                        System.out.println("thread   开始处理数据...");
                        threadpool.submit(new QQServerMessageThread(key)).get();
                        Thread.sleep(100);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("server端------遍历Key异常");

                } finally {
                    //删除旧的key------
                    it.remove();
                }
            }
        }
    }

    /**
     * 广播消息
     */
    public void broadCastMsg( BaseMessage mesg) throws IOException {
        //取出所有： 上线的用户
        Set<String> users = friends.keySet();
        for(String user :users){
            SocketChannel socketChannel = friends.get(user);
            socketChannel.write(ByteBuffer.wrap(mesg.toBytes()));

        }
    }

    /**
     * 转发消息
     */
    public void forwardMsg(BaseMessage mesg, String user) throws IOException {

        SocketChannel socketChannel = friends.get(user);
        if (socketChannel!=null){
            socketChannel.write(ByteBuffer.wrap(mesg.toBytes()));
        }
    }

    public void forwardFriends(SocketChannel socketChannel) throws IOException {
        ServerFreshFriends freshFriends = new ServerFreshFriends();
        freshFriends.setFriends(getFriendsBytes());

        broadCastMsg(freshFriends);
    }
}
