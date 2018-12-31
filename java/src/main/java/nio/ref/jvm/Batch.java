package nio.ref.jvm;

import java.io.IOException;

public class Batch {

    public static void main(String[] args) throws IOException, InterruptedException {


        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java -cp /home/centos/ a ");
        process.waitFor();

        int read = process.getInputStream().read();


//        System.out.println("a");
    }
}
