package threadTest.beeBear;


public class Bear  extends Thread{
    Containor con;

    public Bear(Containor con) {
        this.con = con;
    }

    @Override
    public void run() {

        while (true)
        con.clearAll();
    }
}
