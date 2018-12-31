package hadoop;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;

public class Zk {
    
    @Test
    public void getzk() throws IOException, KeeperException, InterruptedException {
         final String connstr = "localhost:2181";
        ZooKeeper zk = new ZooKeeper(connstr,10000,null);

        Stat stat = new Stat();
        byte[] data = zk.getData("/", false, stat);

        System.out.println(new String(data));

        System.out.println("stat---"+stat.toString());
        System.out.println("========");
        System.out.println(stat.getAversion());

    }

    @Test
    public void iterZk() throws IOException {
        String host="localhost:2181";
        //监听器
        Watcher watcher = new Watcher(){
            public void process(WatchedEvent event) {
                System.out.println(event.toString());
            }
        };
        ZooKeeper zk = new ZooKeeper(host, 10000,null );



    }
}
