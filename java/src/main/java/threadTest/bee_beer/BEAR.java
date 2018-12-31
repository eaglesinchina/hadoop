package threadTest.bee_beer;

/**
 * 蜜蜂：100 --》生成力： 1
 * <p>
 * 容器：  50
 * <p>
 * 熊： 2  ---》消费力： >=20吃光： 容器的蜜
 */
public class BEAR extends Thread {

    //属性
    Container con;

    public BEAR(Container con) {
        this.con = con;
    }


    @Override
    public void run() {

        //生产数据
        while (true) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private void consume() throws InterruptedException {

        synchronized (con) {
            while (con.getCount() < 20) {


                    con.wait();
                }
//                try {
//                    con.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (con.getCount() >= 20) {


                    System.out.println("熊吃完了。。。。" + con.getCount());
                    con.setCount(0);
                    con.notifyAll();

            }
        }
    }


