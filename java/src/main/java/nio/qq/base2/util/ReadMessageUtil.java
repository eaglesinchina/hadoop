package nio.qq.base2.util;

import qq.base2.*;
import nio.qq.base2.bussiness.ser.QQserver;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.List;

public class ReadMessageUtil {
    //属性： 上传的文件
    static FileMessage file;

    public static BaseMessage serverReadClientMsg(SelectionKey key) throws Exception {
        //1，读： 头部
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buf1 = ByteBuffer.allocate(1);

        int len = channel.read(buf1);

        //2,取出头部--判断消息类型
        buf1.flip();
        byte headType = buf1.get(0);
        System.out.println("ReadMesgUtil--ser---headType=" + headType);

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


            case BaseMessage.FILE_ANSWER_YES_CLI: {
                /**--->
                 *      类型 | 存储路径长度 |存储路径
                 * 	------------------------------
                 * 	    1    |1           |n
                 */

                //获取： 文件接收者的ip, 把文件转发给对方
                FileReceiveYesServer ansyes = new FileReceiveYesServer();
                //存储路径
                buf1.clear();
                channel.read(buf1);
                buf1.flip();
                byte locationLen = buf1.get(0);
                ByteBuffer bufn = ByteBuffer.allocate(locationLen);
                channel.read(bufn);
                bufn.flip();
                String locationName = new String(bufn.array(), 0, bufn.limit());
                ansyes.setDestLocation(locationName);

                //接收者Ip
                ansyes.setReceiverIp(QQutils.getRemotAddrrHostname(channel));

                /**-返回数据 --->
                 *      类型 |文件名长度|  文件名 |file长度|  file           | 存储路径长度 |存储路径 | 接收者Ip长度 |接收者Ip
                 * 	------------------------------
                 * 	    1   |1         |    n   |4      |    n            |1           |n        |1           |n
                 */
                //文件信息
                ansyes.setFile(file);
                System.out.println( "server ReadMessageUtil====> locationName "+ locationName  +"  , 文件名="+file.getFileName());
                return ansyes;
            }

            case BaseMessage.FILE: {

/**源数据
 *          	        内容	|内容长度 |文件名|文件名长度 | 类型
 * 			------------------------------
 * 			             n  |   4  	 |	n  |        1 | 1
 */
                file = new FileMessage();
                //name
                buf1.clear();
                channel.read(buf1);
                buf1.flip();
                byte nameLen = buf1.get(0);
                ByteBuffer bufn = ByteBuffer.allocate(nameLen);
                channel.read(bufn);
                bufn.flip();
                String filename = new String(bufn.array(), 0, bufn.limit());

                //content
                ByteBuffer buf4 = ByteBuffer.allocate(4);
                channel.read(buf4);
                buf4.flip();
                int contentLen = NumberUtils.byte2Int(buf4.array());
                bufn = ByteBuffer.allocate(contentLen);
                channel.read(bufn);
                bufn.flip();
/**生产数据格式：
 *          	        内容	|内容长度 |文件名|文件名长度   |发送者|发送者长度  | 类型
 * 			------------------------------
 * 			             n  |   4  	 |	n  |        1   |n     |1        | 1
 *
 */
                file.setFileName(filename);
                file.setFileContent(bufn.array());
                file.setSenderAddr(QQutils.getRemotAddrrHostname(channel));
                return file;
            }
        }//switch

        return null;
    }

    public static BaseMessage clientReadServerMsg(Socket socket) throws Exception {
        //1，读： 头部
        InputStream in = socket.getInputStream();
        byte[] buf1 = new byte[1];
        in.read(buf1);
        int headType = buf1[0];
        System.out.println("ReadMesgUtil--cli---headType=" + headType);

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
                byte[] bufn = new byte[buf1[0]];
                in.read(bufn);
                String sendAddr = new String(bufn);

                //内容多长4byte ， 内容nbyte
                byte[] buf4 = new byte[4];
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
                int sendAddrLen = buf1[0];
                byte[] bufn = new byte[sendAddrLen];
                in.read(bufn);
                String sendAddr = new String(bufn);


                //receive
                in.read(buf1);
                int receiveAddrLen = buf1[0];
                bufn = new byte[receiveAddrLen];
                in.read(bufn);
                String receiveAddr = new String(bufn);

                //内容多长4byte ， 内容nbyte
                byte[] buf4 = new byte[4];
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
                byte[] buf4 = new byte[4];
                in.read(buf4);
                int contentLen = NumberUtils.byte2Int(buf4);
                byte[] bufn = new byte[contentLen];
                in.read(bufn);
                List<String> list = QQutils.deserFriends(bufn);


                clientFreshFriends.setFriends(list);
                return clientFreshFriends;
            }

            case BaseMessage.FILE: {
/**cli:
 *          	        内容	|内容长度 |文件名|文件名长度   |发送者|发送者长度  | 类型
 * 			------------------------------
 * 			             n  |   4  	 |	n  |        1   |n     |1        | 1
 *
 */
                FileMessage file = new FileMessage();
                //sender
                in.read(buf1);
                byte addrLen = buf1[0];
                byte[] bufn = new byte[addrLen];
                in.read(bufn);
                String senderAddr = new String(bufn);

                //name
                in.read(buf1);
                byte nameLen = buf1[0];
                bufn = new byte[nameLen];
                in.read(bufn);
                String filename = new String(bufn);

                //content
                byte[] buf4 = new byte[4];
                in.read(buf4);
                int contentLen = NumberUtils.byte2Int(buf4);
                bufn = new byte[contentLen];
                in.read(bufn);

                file.setFileName(filename);
                file.setFileContent(bufn);
                file.setSenderAddr(senderAddr);
                return file;
            }//switch

            case BaseMessage.FILE_ANSWER_YES_SERVer:{
                //解析文件： 写入到本地磁盘
                /**-返回数据 --->
                 *      类型 |文件名长度|  文件名 |file长度|  file           | 存储路径长度 |存储路径 | 接收者Ip长度 |接收者Ip
                 * 	------------------------------
                 * 	    1   |1         |    n   |4      |    n            |1           |n        |1           |n
                 */
                //文件名
                in.read(buf1);
                byte nameLen = buf1[0];
                byte[] bufn = new byte[nameLen];
                in.read(bufn);
                String fileName=new String(bufn);

                //文件内容
                byte[] buf4=new byte[4];
                in.read(buf4);
                int fileLen = NumberUtils.byte2Int(buf4);

                bufn = new byte[fileLen];
                in.read(bufn);


                //文件存储路径
                in.read(buf1);
                byte locationLen = buf1[0];
                byte[] locationBuf = new byte[locationLen];
                in.read(locationBuf);
                String location=new String(locationBuf);

                //接收者Ip
                in.read(buf1);
                byte ipLen = buf1[0];
                byte[] ipBuf = new byte[ipLen];
                in.read(ipBuf);
                String receiveIP=new String(ipBuf);


                //文件拷贝==============
                FileOutputStream out = new FileOutputStream(location + "/" + fileName);
                out.write(bufn);
                out.close();


                //返回文件： 写入成功的提示
                ClientChats clichats = new ClientChats();
                clichats.setSenderAddr(receiveIP);
                clichats.setMesg(receiveIP +"  文件接收成功  -->" +fileName);
                return clichats;
            }
        }
        return null;
    }
}
