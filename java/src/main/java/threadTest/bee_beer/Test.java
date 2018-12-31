package threadTest.bee_beer;

public class Test {

    public static void main(String[] args) {
        Container con = new Container();

        new BEAR(con).start();
        new BEAR(con).start();

        for (int i=0;i<3;i++){

            new Bee(con).start();
        }

    }
}
