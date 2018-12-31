package nio.qq.base.util;

import qq.base.*;
import nio.qq.base.bussiness.cli.QQClientCommThread;
import nio.qq.base.bussiness.ser.QQserver;

import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

public class ReadMessageUtil {

    public static BaseMessage serverReadClientMsg(SelectionKey key) throws Exception {
        //1，读： 头部
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buf1 = ByteBuffer.allocate(1);

        int len = channel.read(buf1);

        //2,取出头部--判断消息类型
        buf1.flip();
        byte headType = buf1.get(0);
        System.out.println("ReadMesgUtil--ser---headType="+headType);

        switch (headType) {

            //3，群聊
            case BaseMessage.CLIENT_CHATS: {
                ServerChats serchats = new ServerChats();
                //多长4byte ， 内容nbyte
                ByteBuffer buf4 = ByteBuffer.allocate(4);
                channel.read(buf4);
                buf4.flip();
                int contentLen = NumberUtils.byte2Int(buf4.array());

                //3, 读取内容
                ByteBuffer buf = ByteBuffer.allocate(contentLen);
                channel.read(buf);
                buf.flip();
                //封装： 新消息对象

                serchats.setMesg(buf.array());
                serchats.setReceiveAddr(QQutils.getRemotAddrrHostname(channel).getBytes());
                System.out.println("ser工具类：  群聊消解析送完毕.....");
                return serchats;
            }

            //3,私聊
            case BaseMessage.CLIENT_CHAT: {
                /**
                 * 源数据：     /**
                 *               内容	|   	内容长度 |reveive地址	|receive地址长度   | 类型
                 *       		-----------------------------------------------------------------
                 *       			n	|		4       |n		    |   1           | 1  |
                 */


                ServerChat serchat = new ServerChat();

                //recv地址长
                buf1.clear();
                channel.read(buf1);
                buf1.flip();

                byte receliveAddrLen = buf1.get(0);
                ByteBuffer bufn = ByteBuffer.allocate(receliveAddrLen);
                channel.read(bufn);
                bufn.flip();
                serchat.setReveiveAddr(bufn.array());


                //多长4byte ， 内容nbyte
                ByteBuffer buf4 = ByteBuffer.allocate(4);
                channel.read(buf4);
                buf4.flip();
                byte[] arr = buf4.array();
                int contentLen = NumberUtils.byte2Int(arr);

                ByteBuffer buf = ByteBuffer.allocate(contentLen);
                channel.read(buf);
                buf.flip();

                serchat.setMesg(buf.array());
                serchat.setSenderAddr(QQutils.getRemotAddrrHostname(channel).getBytes());
                System.out.println("server工具类---解析后的私聊:sermsg:  send=" + new String(serchat.getSenderAddr()) +
                        ",receive=" + new String(serchat.getReveiveAddr()));
                return serchat;

                /**  内容	        |   	内容长度	|receive地址	|receive地址长度 |send地址	|send地址长度   | 类型
                 * 			-----------------------------------------------------------------
                 * 				n	|		4       |n		    |   1          |n		    |   1           | 1  |
                 */
            }
            //3,刷新：好友列表
            case BaseMessage.CLIENT_REFRESHFRIENDS: {
                ServerFreshFriends serverFreshFriends = new ServerFreshFriends();
                byte[] bytes = QQserver.getInstance().getFriendsBytes();
                serverFreshFriends.setFriends(bytes);

                return serverFreshFriends;
            }
        }//switch

        return null;
    }

    public static BaseMessage clientReadServerMsg(Socket socket) throws Exception {
        //1，读： 头部
        InputStream in = socket.getInputStream();
        byte[] buf1=new byte[1];
        in.read(buf1);
        int headType =buf1[0];
        System.out.println("ReadMesgUtil--cli---headType="+headType);

        Thread.sleep(1000);

        switch (headType) {
            /**
             * 数据源格式：    内容	|	  内容长度	|send地址	|send地址长度   | 类型
             *  * 			-----------------------------------------------------------------
             *  * 				n	|		4       |n		    |   1           | 1  |
             */

            //3，群聊
            case BaseMessage.SERVER_CHATS: {
                ClientChats clichats = new ClientChats();
                //send地址1  	|send地址长度n

                in.read(buf1);
                byte[] bufn=new byte[ buf1[0]];
                in.read(bufn);
                String sendAddr = new String(bufn);

                //内容多长4byte ， 内容nbyte
               byte[] buf4=new byte[4];
                in.read(buf4);
                int contentLen = NumberUtils.byte2Int(buf4);

                //3, 读取内容
                bufn = new byte[contentLen];
                in.read(bufn);
                String mesg = new String(bufn);
                //封装： 新消息对象

                clichats.setMesg(mesg);
                clichats.setSenderAddr(sendAddr);
                return clichats;
            }

            /**  源数据	        |   	内容长度	|receive地址	|receive地址长度 |send地址	|send地址长度   | 类型
             * 			-----------------------------------------------------------------
             * 				n	|		4       |n		    |   1          |n		    |   1           | 1  |
             */
            //3,私聊
            case BaseMessage.SERVER_CHAT: {
                System.out.println("cli---接收私聊...");
                ClientChat clichat = new ClientChat();
                //send地址1  	|send地址长度n
                in.read(buf1);
                int sendAddrLen =buf1[0];
                byte[] bufn=new byte[sendAddrLen];
                in.read(bufn);
                String sendAddr = new String(bufn);


                //receive
                in.read(buf1);
                int receiveAddrLen =buf1[0];
                bufn=new byte[receiveAddrLen];
                in.read(bufn);
                String receiveAddr = new String(bufn);

                //内容多长4byte ， 内容nbyte
                byte[] buf4=new byte[4];
                in.read(buf4);
                int contentLen = NumberUtils.byte2Int(buf4);
                bufn = new byte[contentLen];
                in.read(bufn);
                String mesg = new String(bufn);
                //封装： 新消息对象

                clichat.setMesg(mesg);
                clichat.setSenderAddr(sendAddr);
                clichat.setReceiveAddr(receiveAddr);
                return clichat;
            }
            //3,刷新：好友列表
            case BaseMessage.SERVER_REFRESHFRIENDS: {
                ClientFreshFriends clientFreshFriends = new ClientFreshFriends();
                /**
                 * 		                内容	|长度   | 类型
                 * 			------------------------------
                 * 			|		n		|   4   | 1  |
                 */
                byte[] buf4=new byte[4];
                in.read(buf4);
                int contentLen = NumberUtils.byte2Int(buf4);
                byte [] bufn = new byte[contentLen];
                in.read(bufn);
                List<String> list = QQutils.deserFriends(bufn);


                clientFreshFriends.setFriends(list);
                return clientFreshFriends;
            }
        }//switch

        return null;
    }

}
