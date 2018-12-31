package nio.lock;

import java.util.concurrent.locks.ReentrantLock;

public class Ticket {

    //属性
    Ticket t;
   static int ticketCount=1000000;
    //锁对象
    static  ReentrantLock lock=new ReentrantLock();

    private Ticket() { }

    //单利模式
    public Ticket getInstance(){
        if (t !=null) return t;
        else {

            if (t ==null){
                lock.lock();
                t=new Ticket();
                lock.unlock();
            }
            return t;
        }
    }

    //方法
    public static int reduceTicket(){
        //上锁
        boolean flag = lock.tryLock();
        if(! flag) return 0;

        //解锁
        if(ticketCount ==0){ lock.unlock(); return -1; }
        else { lock.unlock();  return ticketCount --; }

    }

}
