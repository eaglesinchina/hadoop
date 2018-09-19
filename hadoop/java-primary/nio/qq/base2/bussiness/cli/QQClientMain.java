package qq.base2.bussiness.cli;

/**
 * Created by Administrator on 2018/9/10.
 */
public class QQClientMain {
	public static void main(String[] args) {
		QQClientChatsUI ui = new QQClientChatsUI();
		QQClientCommThread t = null;
		try {
			t = new QQClientCommThread(ui);
		} catch (Exception e) {
			e.printStackTrace();
		}


		ui.qqcli=t;

		t.start();

	}
}
