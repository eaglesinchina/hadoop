package qq.base.bussiness.ser;

import qq.base.BaseMessage;
import qq.base.ServerChat;
import qq.base.ServerChats;
import qq.base.util.QQutils;
import qq.base.util.ReadMessageUtil;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReentrantLock;

public class QQServerMessageThread implements  Runnable{
    private SelectionKey key;
    private QQserver qqserver=QQserver.getInstance();


    public QQServerMessageThread(SelectionKey key) {
        this.key = key;
    }


    @Override
    public void run() {
        //加锁
        ReentrantLock lock= (ReentrantLock) key.attachment();
        boolean flag = lock.tryLock();

        //读取key的内容
        if (flag){

            try {
                BaseMessage mesg = ReadMessageUtil.serverReadClientMsg(key);
                System.out.println("服务器thread: parseMsg 后的消息type = "+mesg.getMsgType());
                
                
                //转发消息
                switch (mesg.getMsgType()){
                    case BaseMessage.SERVER_CHATS:
                        qqserver.broadCastMsg(mesg);
                        System.out.println("服务器： 发送完chats 。。。");
                        break;

                    case  BaseMessage.SERVER_CHAT:
                        ServerChat chat= (ServerChat) mesg;
                        qqserver.forwardMsg(chat, new String(chat.getReveiveAddr()));
                        System.out.println("服务器： 发送完chat 。。。"+chat.getReveiveAddr()+",sender="+chat.getSenderAddr());
                         break;

                    case  BaseMessage.SERVER_REFRESHFRIENDS:
                        qqserver.forwardFriends((SocketChannel) key.channel());
                        break;
                }


            } catch (Exception e) {
                //  删除好友========
                key.cancel();

                try {
                    SocketChannel  socketChannel = (SocketChannel) key.channel();
                    String hostname = QQutils.getRemotAddrrHostname(socketChannel);

                    System.out.println(hostname+"  下线了");
                    qqserver.friends.remove(QQutils.getRemotAddrrHostname(socketChannel));

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
            lock.unlock();
        }



    }
}
