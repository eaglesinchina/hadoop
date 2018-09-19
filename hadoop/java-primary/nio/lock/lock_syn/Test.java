package lock.lock_syn;

public class Test {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();
        new Sailer("a",ticket).start();
        new Sailer("b",ticket).start();
        new Sailer("c",ticket).start();
        new Sailer("d",ticket).start();
    }

}
