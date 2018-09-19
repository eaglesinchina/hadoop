package day02.homework.beeBear;

public class Bee extends  Thread {

    Containor con;

    public Bee(Containor con) {
        this.con = con;
    }

    @Override
    public void run() {

        while (true)
            con.add();
    }
}
