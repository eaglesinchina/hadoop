package day02.homework.heshang;

/**
 * * 和尚： 30 --->[1,4]
 * * 馒头： 100
 * *
 * * 求： 最短时间消费完　?
 * * 预留　３０个馒头：　join()
 */
public class Heshang2 extends Thread {

    public static int mantouCount = 100;
    int eatCount = 0;
    static int leftHeshang = 30;


    @Override
    public void run() {

        while (true) {
            synchronized (this) {

                //吃了４个, 休眠等待
                if (mantouCount <= 0 || this.eatCount == 4) {
                    //终止消费
                    break;

                } else {
                    //消费：临界点
                    if (mantouCount == leftHeshang) {
                        if (this.eatCount == 0) {
                            leftHeshang--;
                            mantouCount--;
                            this.eatCount++;
                        }


                    } else {
                        //继续消费
                        if (this.eatCount == 0) {
                            leftHeshang--;
                        }
                        System.out.println(Thread.currentThread().getName() + " eat: 第 " + (this.eatCount + 1) + " 个馒头-->" + mantouCount);
                        mantouCount--;
                        this.eatCount++;
                    }
                }

            }
        }//while
    }
}
