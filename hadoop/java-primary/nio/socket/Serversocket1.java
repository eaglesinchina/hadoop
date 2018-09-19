package tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class Serversocket1 {

    public static void main(String[] args) throws IOException {
        //服务器端： 监听
        ServerSocket server = new ServerSocket(8888);

        while (true){

            Socket socket = server.accept();
            new Mythread(socket).start();
        }
        //创建线程x
    }

    static class Mythread extends  Thread{
        //属性
        Socket socket;
        public Mythread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream in = socket.getInputStream();
                //读取数据
                BufferedInputStream br = new BufferedInputStream(in);
                byte [] buf=new byte[1024];
                int len=0;

                //解析ip
                InetSocketAddress inet =(InetSocketAddress) (socket.getRemoteSocketAddress());
                String host = inet.getHostName();
                int port = inet.getPort();

                System.out.printf("接收到：  %s %d--->" , host,port);
                while ((len=br.read(buf)) !=-1){
                    System.out.println(new String(buf,0,len));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
