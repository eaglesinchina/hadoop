package threadTest.beeBear;

public class Test {

    public static void main(String[] args) {

        Containor con = new Containor();


        new Bear(con).start();
        new Bear(con).start();

        new Bee(con).start();
    }
}
