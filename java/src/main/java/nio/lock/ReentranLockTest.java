package nio.lock;

public class ReentranLockTest {

    public static void main(String[] args) {
        new Sailer("a").start();
        new Sailer("b").start();
        new Sailer("d").start();
        new Sailer("c").start();
    }

}
