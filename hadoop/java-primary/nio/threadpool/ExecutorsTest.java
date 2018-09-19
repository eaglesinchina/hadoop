package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsTest {
static  int i=1;

    public static void main(String[] args) {

        //创建：线程池--->Executors.newfixed...
        ExecutorService pool = Executors.newFixedThreadPool(2);
        int n=10;

        for (  int i=0;i<n;i++) {

            pool.execute(new T(i));
        }


        pool.shutdown();
        System.out.println(pool.isShutdown());

    }

}
//-------------------
class  T implements Runnable{
    int i;

    public T(int i) {
        this.i = i;
    }

    @Override
    public void run() {
        System.out.println("hello "+(i++)+ "| "+Thread.currentThread().getName());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}