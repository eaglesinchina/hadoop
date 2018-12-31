package nio.lock.lock_syn;

public class Sailer  extends  Thread{
    long start=System.currentTimeMillis();

    //属性
    String name;
    Ticket ticket;

    public Sailer(String name, Ticket ticket) {
        this.name = name;
        this.ticket=ticket;
    }

    //方法

    @Override
    public void run() {
        while (true) {
            int i = ticket.reduceTicket();

             if (i==-1) {
                System.out.println("syn time--"+ (System.currentTimeMillis()-start));
                return;
            }
            else System.out.printf("%s:  %d \r\n",name, i);
        }
    }
}
