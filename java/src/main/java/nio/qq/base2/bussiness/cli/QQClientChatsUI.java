package nio.qq.base2.bussiness.cli;

import nio.qq.base2.ClientChats;
import nio.qq.base2.FileMessage;
import nio.qq.base2.FileReceiveYesCli;
import nio.qq.base2.FileReceiveYesServer;
import qq.base2.util.QQutils;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
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
    public QQClientCommThread qqcli;
    //历史聊天区
    private JTextArea taHistory;
    JTextArea fileArea;

    //好友列表
    private JList<String> lstFriends;

    //消息输入区
    private JTextArea taInputMessage;

    //发送按钮
    private JButton btnSend;

    //刷新好友列表按钮
    private JButton btnRefresh;
    JButton fileButton;

    public QQClientChatsUI() {
        init();
        this.setVisible(true);
    }


    /**
     * 初始化布局
     */
    //所有私聊窗口
    public Map<String, QQClientChatSingleUI> allSingleChart = new HashMap<String, QQClientChatSingleUI>();
//	public Map<String,JframeFile> fileUpdateMap = new HashMap<String,JframeFile>() ;

    private void init() {
        this.setTitle("QQClient");
        this.setBounds(100, 100, 800, 600);
        this.setLayout(null);

        //历史区
        taHistory = new JTextArea();
        taHistory.setBounds(0, 0, 420, 300);

        JScrollPane sp1 = new JScrollPane(taHistory);
        sp1.setBounds(0, 0, 420, 300);
        this.add(sp1);

        //lstFriends： 好友列表区
        lstFriends = new JList<String>();
        lstFriends.setBounds(450, 0, 260, 200);
        lstFriends.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                //鼠标： 双击事件
                if (e.getClickCount() == 2) {
                    String recvAddr = lstFriends.getSelectedValue();
                    QQClientChatSingleUI singleUI = allSingleChart.get(recvAddr);

                    if (singleUI == null) {
                        singleUI = new QQClientChatSingleUI(recvAddr, qqcli);
                        allSingleChart.put(recvAddr, singleUI);
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

        //taInputMessage：  文本输入框
        taInputMessage = new JTextArea();
        taInputMessage.setBounds(0, 420, 550, 160);
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

        //文件名： 输入框
        fileArea = new JTextArea();
        fileArea.setBounds(420, 220, 250, 160);
        this.add(fileArea);

        fileButton = new JButton("传文件");
        fileButton.setBounds(680, 220, 100, 160);
        fileButton.addActionListener(this);
        this.add(fileButton);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(-1);
            }
        });
    }

    /**
     * 发送消息
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //获取输入的文本
        if (source == btnSend) {
            String txt = taInputMessage.getText();


            //发送文本
            try {

                //yes:/home/centos/aaa
                if (txt.toLowerCase().startsWith("yes")){
                    String destLocation = txt.split(":")[1];

                    FileReceiveYesCli answer = new FileReceiveYesCli();
                    answer.setDestLocation(destLocation);
                    qqcli.sendMsg(answer);
                    System.out.println("通知server：  我要接收文件 "+destLocation);

                }else{
                    //封装消息对象
                    ClientChats mesg = new ClientChats();
                    mesg.setMesg(txt);
                    qqcli.sendMsg(mesg);
                    System.out.println("发送群聊消息成功");
                }


            } catch (Exception e1) {
                e1.printStackTrace();
            }

            taInputMessage.setText("");
        }
        if (source == fileButton) {
            String txt = fileArea.getText();

            String filename = fileArea.getText();
            File file = new File(filename);
            System.out.println("文件是: " + filename);
            FileMessage fileMessage = new FileMessage();
            fileMessage.setFileName(file.getName());

            try {
                fileMessage.setFileContent(file);
                //发送文件
                qqcli.sendMsg(fileMessage);
                System.out.println("发送文件成功");
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            fileArea.setText("");
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
    public void updateHistory(String who, String msg) {
        taHistory.append("[" + who + "]说:\r\n");
        String formatStr = msg.replace("\n", "\n\t");
        formatStr = "\t" + formatStr + "\r\n";
        taHistory.append(formatStr);
    }

    /**
     * 文件接收提示：
     */
    public void receiveFile(String who, FileMessage file) {
        taHistory.append("[" + who + "]上传了文件:---> " + file.getFileName() + "    是否接收？  (yes/no：文件夹)\r\n");
    }
}
