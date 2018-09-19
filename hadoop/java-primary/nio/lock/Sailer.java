package lock;

public class Sailer  extends  Thread{
    long start=System.currentTimeMillis();

    //属性
    String name;
    public Sailer(String name) {
        this.name = name;
    }

    //方法
    @Override
    public void run() {
        while (true) {
            int i = Ticket.reduceTicket();

            if (i==0) continue;
            else if (i==-1) {
                System.out.println("time--"+ (System.currentTimeMillis()-start));
                return;
            }
            else System.out.printf("%s:  %d \r\n",name, i);
        }
    }
}
