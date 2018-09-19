package qq.base.bussiness.cli;

import qq.base.ClientChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 客户端私聊界面
 */
public class QQClientChatSingleUI extends JFrame implements ActionListener {

	//通信线程
	private final QQClientCommThread qqcli;
	//历史聊天区
	private JTextArea taHistory;
	//消息输入区
	private JTextArea taInputMessage;
	//发送按钮
	private JButton btnSend;
	//接受者信息
	private String recvAddr;

	public QQClientChatSingleUI(String recvAddr,QQClientCommThread commThread) {
		this.qqcli = commThread ;
		this.recvAddr = recvAddr;
		init();
		this.setTitle("【私聊】我@--->"+recvAddr+" ：");
		this.setVisible(true);
	}

	/**
	 * 初始化布局
	 */
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

		//taInputMessage
		taInputMessage = new JTextArea();
		taInputMessage.setBounds(0, 420, 540, 160);
		this.add(taInputMessage);

		//btnSend
		btnSend = new JButton("发送");
		btnSend.setBounds(560, 420, 100, 160);
		btnSend.addActionListener(this);
		this.add(btnSend);
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
    public Map<String,QQClientChatSingleUI> allSingleChart = new HashMap<String,QQClientChatSingleUI>() ;
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		//发送按钮
		if (source == btnSend) {
			String str = taInputMessage.getText();


				ClientChat msg = new ClientChat();
				msg.setMesg(taInputMessage.getText());
				msg.setReceiveAddr(recvAddr);
                System.out.println("mesg=="+msg.getMsgType()+","+msg.getReceiveAddr());

				try {
                    qqcli.sendMsg(msg);
				} catch (Exception e1) {
					System.out.println("发送失败 : "  + e1.getMessage());
					e1.printStackTrace();
				}
				taInputMessage.setText("");
				updateHistory("我" , str );
		}
	}

	/**
	 * 更新历史区域内容
	 */
	public void updateHistory(String who , String msg) {
		taHistory.append("[" + who + "]说:\r\n");
		String formatStr = msg.replace("\n", "\n\t");
		formatStr = "\t" + formatStr + "\r\n";
		taHistory.append(formatStr);
	}
}