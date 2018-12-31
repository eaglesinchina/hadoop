package nio.lock.twoThreadLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LockTest {

    /**
     * 创建线程：   同时用锁
     */

    public static void main(String[] args) {


        ExecutorService exec = Executors.newFixedThreadPool(3);
        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });

        exec.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello2");
            }
        });

    }

}
