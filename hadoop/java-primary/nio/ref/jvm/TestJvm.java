package ref.jvm;

import org.junit.Test;

public class TestJvm {

    @Test
    public void t() {
//        printSelf(1);

        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024);


    }

    public void printSelf(int x) {
        System.out.println(x);
        printSelf(++x);
    }

    public static void main(String[] args) throws Exception {

        Class<?> app2 = Class.forName("ref.jvm.TestGc");
        System.out.println();
        while (true) {
            System.out.println(2);
            Thread.sleep(100);
        }
    }
}
