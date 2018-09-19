package test1;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Myzk2 {
    


    //查看stat: 状态信息
    @Test
    public void getStat() throws Exception {


//         Watcher watcher = new Watcher() {
//            public void process(WatchedEvent event) {
//                System.out.println("process:==>"+event.getType().toString());
//            }
//        };


        //like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("process-->"+event.getType().toString());
            }
        });

        //get
        Stat stat = new Stat();
      // zk.getData("/a",true,stat);

        System.out.println("stat==="+stat);

        while (true) Thread.sleep(100);
    }

    //查看stat: 状态信息
    @Test
    public void getStat2() throws Exception {

//         Watcher watcher = new Watcher() {
//            public void process(WatchedEvent event) {
//                System.out.println("process:==>"+event.getType().toString());
//            }
//        };


        //like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
        String conn="s104:2181,s102:2181,s103:2181";
       final ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        //get
       final Stat stat = new Stat();
         zk.getData("/a", new Watcher() {
             public void process(WatchedEvent event) {
                 System.out.println("process:==>"+event.getType().toString());

                 try {
                     zk.getData("/a",this,stat);//watcher  在zk中， 重新注册
                 } catch (Exception e) {
                     e.printStackTrace();
                 }

             }
         }, stat);


        System.out.println("stat==="+stat);

        while (true) Thread.sleep(100);
    }
}
