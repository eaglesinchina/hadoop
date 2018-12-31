package threadTest.beeBear;

public class Containor {

    static int Max=50;
    int honeyCapa;

    //方法
    public synchronized void add(){

        if(honeyCapa ==Max){//等待

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else {//添加

            honeyCapa++;
            System.out.println(Thread.currentThread().getName()+"生产---"+honeyCapa);
            this.notifyAll();
        }

    }
    //方法
    public  synchronized  void clearAll(){
            if(honeyCapa <20){//等待
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else {//消費
                System.out.println(Thread.currentThread().getName()+"=============消費---"+honeyCapa);
                honeyCapa=0;
                notifyAll();
            }
    }

}
