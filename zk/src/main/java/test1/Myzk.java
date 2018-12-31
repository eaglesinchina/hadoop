package test1;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class Myzk {

    //查看： 子节点
    @Test
    public void getChild() throws Exception {
        //like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        
        //get
        Stat stat = new Stat();
        List<String> child = zk.getChildren("/", false, stat);

        for (String s : child) {
            System.out.println(s);
            
        }
        org.apache.zookeeper.server.quorum.QuorumPeerMain;

        System.out.println("stat==="+stat);
    }
    //查看stat: 状态信息
    @Test
    public void getStat() throws Exception {
        //like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        //get
        Stat stat = new Stat();
       zk.getData("/a",false,stat);

        System.out.println("stat==="+stat);
    }



    //添加节点： 数据
    @Test
    public void create() throws IOException, KeeperException, InterruptedException {
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        zk.create("/a/c", "hello 你俩好".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    //删除节点
    @Test
    public void del() throws IOException, KeeperException, InterruptedException {
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        zk.delete("/a/c",0);

    }

    @Test
    public void reset() throws IOException, KeeperException, InterruptedException {
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);

        zk.setData("/a","new data".getBytes(),-1);
    }

}
