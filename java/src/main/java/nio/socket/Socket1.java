package nio.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Socket1 {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("0.0.0.0", 8888);
        OutputStream out = socket.getOutputStream();
        int i=1;
        for( ;;){

            out.write(("yyy "+i ).getBytes());
            out.write("\r\n".getBytes());
            out.flush();
            i++;

            Thread.sleep(100);
        }




    }
}
