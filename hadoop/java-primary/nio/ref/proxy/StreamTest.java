package ref.proxy;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StreamTest {

    @Test
    public void t() throws IOException {


        //基本流
        int res = new FileInputStream("").read();

        //包装流
        int len = new BufferedInputStream(new FileInputStream("")).read();
    }
}
