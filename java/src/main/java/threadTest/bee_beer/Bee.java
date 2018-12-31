package threadTest.bee_beer;

/**
 * 蜜蜂：100 --》生成力： 1
 * <p>
 * 容器：  50
 * <p>
 * 熊： 2  ---》消费力： >=20吃光： 容器的蜜
 */
public class Bee extends Thread {

    //属性
    Container con;

    public Bee(Container con) {
        this.con = con;
    }

    @Override
    public void run() {
        //生产数据
        while (true) {

            produce();
        }
    }


    private void produce() {

        synchronized (con) {
            while (con.getCount() >= 50) {

                    try {
                        con.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
                    con.setCount(con.getCount() + 1);
                    System.out.println(Thread.currentThread().getName() + "  生成蜂蜜 ...count=" + con.getCount());

                    con.notifyAll();

                    try {
                        Thread.sleep(1300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }





        }


    }
}
