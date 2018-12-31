package threadTest.heshang;

/**
 * * 和尚： 30 --->[1,4]
 * * 馒头： 100
 * *
 * * 求： 最短时间消费完　?
 * * 预留　３０个馒头：　join()
 */
public class Heshang extends Thread {

    public static int mantouCount = 100;
    int eatCount = 0;


    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                if (mantouCount > 0) {

                    System.out.println(Thread.currentThread().getName() + " eat: 第 "+(this.eatCount+1) + " 个馒头-->"  + mantouCount );
                    mantouCount--;
                    this.eatCount++;
                    //吃了４个, 休眠等待
                    if (this.eatCount == 4) {
                        try {
                           this.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Thread.yield();
                } else {

                    Thread.interrupted();
                    break;
                }


            }
        }
    }
}
