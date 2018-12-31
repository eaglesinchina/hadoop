package nio.qq.base2.bussiness.cli;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

public class JframeFile {

    //属性： 选择的文件路径
    QQClientChatsUI ui;
    public JframeFile(QQClientChatsUI ui) {
        this.ui = ui;
    }

    static JFrame f = new JFrame();
    static JButton jb = new JButton("上传");

    public void Show() {
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser();
                if (jfc.showOpenDialog(f) == JFileChooser.APPROVE_OPTION) {
                    //解释下这里,弹出个对话框,可以选择要上传的文件,如果选择了,就把选择的文件的绝对路径打印出来,有了绝对路径,通过JTextField的settext就能设置进去了,那个我没写
                    String filePath = jfc.getSelectedFile().getAbsolutePath();
//                    ui.fileArea.setText(filePath);
                    System.out.println("jframefile----->"+filePath);

                    f.dispose();//隐藏弹框
                }


            }
        });
        //这下面的不用在意 一些设置
        f.add(jb);
        f.setLayout(new FlowLayout());
        f.setSize(480, 320);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

//    public static void main(String[] args) {
//        new JframeFile().Show();
//    }

}