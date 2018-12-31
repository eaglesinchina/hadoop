package nio.qq.base2.bussiness.cli;

import qq.base2.*;
import qq.base2.util.Constants;
import qq.base2.util.QQutils;
import qq.base2.util.ReadMessageUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * client通信线程
 */
public class QQClientCommThread extends Thread {
	//
	public QQClientChatsUI ui ;

	Socket s=new Socket(Constants.cliIPValue,Constants.cliPortValue) ;

	public QQClientCommThread(QQClientChatsUI ui) throws Exception {
		this.ui=ui;
		ui.setTitle("【群聊】本机ip:  "+QQutils.getLocalAddrrHostname(s));
	}

	public void run() {
		try {

			for(;;){
				BaseMessage msg = ReadMessageUtil.clientReadServerMsg(s) ;
				if(msg != null){
					System.out.println("收到服务器消息, msgType= "+ msg.getMsgType());
					switch (msg.getMsgType()){
						//群聊
						case BaseMessage.CLIENT_CHATS:
						{
							ClientChats msg0 = (ClientChats) msg;
							//更新历史区
							ui.updateHistory(msg0.getSenderAddr(),msg0.getMesg());
							break ;
						}
						case BaseMessage.CLIENT_CHAT:
						{

							ClientChat msg0 = (ClientChat) msg;
							String sendrAddr = msg0.getSenderAddr();
							String receiveAddr=msg0.getReceiveAddr();
							String msgTxt = msg0.getMesg() ;

							System.out.println(msg0);
							System.out.println("cli----thread: 处理私聊  sender="+ sendrAddr+",receiver="+receiveAddr);


							QQClientChatSingleUI singleUI = ui.allSingleChart.get(sendrAddr) ;
							if(singleUI == null){
								singleUI = new QQClientChatSingleUI(sendrAddr ,this) ;
								ui.allSingleChart.put(sendrAddr, singleUI) ;
							}

							singleUI.setVisible(true);
							singleUI.updateHistory(sendrAddr, msgTxt);
							break ;
						}
						case BaseMessage.CLIENT_REFRESHFRIENDS:
						{
							ClientFreshFriends msg0 = (ClientFreshFriends) msg;
							/**
							 * 过滤： 本机ip
							 */
							String ip_port = QQutils.getLocalAddrrHostname(s);
							System.out.println("本机ip,port: "+ ip_port);
							msg0.getFriends().remove(ip_port);

							ui.refreshFriendList(msg0.getFriends());
							break ;
						}

						case  BaseMessage.FILE:
						{
							FileMessage msg0 = (FileMessage) msg;
							ui.receiveFile(msg0.getSenderAddr(), msg0);
							break;
						}
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMsg(BaseMessage mesg) throws IOException {
		OutputStream out = s.getOutputStream();
		out.write(mesg.toBytes());
		out.flush();
	}

	//发送文件
	public void sendFile(FileMessage mesg) throws IOException {
		OutputStream out = s.getOutputStream();
		out.write(mesg.toBytes());
		out.flush();
	}

}
