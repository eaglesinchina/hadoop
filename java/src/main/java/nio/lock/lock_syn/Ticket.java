package nio.lock.lock_syn;

import java.util.concurrent.locks.ReentrantLock;

public class Ticket {

    //属性
   static int ticketCount=1000000;

    //方法
    public synchronized   int reduceTicket(){
        if(ticketCount ==0){ return -1; }
        else {   return ticketCount --; }
    }

}
