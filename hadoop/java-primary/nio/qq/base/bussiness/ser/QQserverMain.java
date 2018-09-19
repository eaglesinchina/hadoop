package qq.base.bussiness.ser;

public class QQserverMain {

    public static void main(String[] args) {

        try {
            QQserver.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
