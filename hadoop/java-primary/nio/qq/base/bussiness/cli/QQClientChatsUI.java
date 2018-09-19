package qq.base.bussiness.cli;

import qq.base.ClientChats;
import qq.base.util.QQutils;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 客户端群聊界面
 */
public class QQClientChatsUI extends JFrame implements ActionListener {
	/**
	 * 持有cli:  发送消息
	 */
	public  QQClientCommThread qqcli;

	//历史聊天区
	private JTextArea taHistory;

	//好友列表
	private JList<String> lstFriends;

	//消息输入区
	private JTextArea taInputMessage;

	//发送按钮
	private JButton btnSend;

	//刷新好友列表按钮
	private JButton btnRefresh;

	public QQClientChatsUI() {
		init();
		this.setVisible(true);
	}

	/**
	 * 初始化布局
	 */
	//所有私聊窗口
	public Map<String,QQClientChatSingleUI> allSingleChart = new HashMap<String,QQClientChatSingleUI>() ;
	private void init() {
		this.setTitle("QQClient");
		this.setBounds(100, 100, 800, 600);
		this.setLayout(null);

		//历史区
		taHistory = new JTextArea();
		taHistory.setBounds(0, 0, 600, 400);

		JScrollPane sp1 = new JScrollPane(taHistory);
		sp1.setBounds(0, 0, 600, 400);
		this.add(sp1);

		//lstFriends
		lstFriends = new JList<String>();
		lstFriends.setBounds(620, 0, 160, 400);
		lstFriends.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//鼠标： 双击事件
				if(e.getClickCount() == 2){
					String recvAddr = lstFriends.getSelectedValue();
					QQClientChatSingleUI singleUI = allSingleChart.get(recvAddr) ;

					if(singleUI == null){
						singleUI = new QQClientChatSingleUI(recvAddr,qqcli) ;
						allSingleChart.put(recvAddr , singleUI) ;
					}

					try {
						System.out.println(QQutils.getLocalAddrrHostname(qqcli.s) + "开始私聊---receive=" + recvAddr);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					singleUI.setVisible(true);
				}

			}
		});


		this.add(lstFriends);

		//taInputMessage
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);

		//btnSend
		btnSend = new JButton("发送");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);

		//btnRefresh
		btnRefresh = new JButton("刷新");
		btnRefresh.setBounds(680, 420, 100, 160);
		btnRefresh.addActionListener(this);
		this.add(btnRefresh);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(-1);
			}
		});
	}

	/**
	 * 发送消息
	 * @param e
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//获取输入的文本
		if(source == btnSend) {
			String txt = taInputMessage.getText();

			//封装消息对象
			ClientChats mesg = new ClientChats();
			mesg.setMesg(txt);


			//发送文本
			try {
				qqcli.sendMsg( mesg);
				System.out.println("发送成功");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			taInputMessage.setText("");
		}

	}

	/**
	 * 刷新好友列表
	 */
	public void refreshFriendList(List<String> list) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (String s : list) {
			listModel.addElement(s);
		}
		lstFriends.setModel(listModel);
	}

	/**
	 * 更新历史区域内容
	 */
	public void updateHistory(String who ,String msg) {
		taHistory.append("[" + who + "]说:\r\n");
		String formatStr = msg.replace("\n", "\n\t");
		formatStr = "\t" + formatStr + "\r\n";
		taHistory.append(formatStr);
	}
}
