package test1;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

public class Myzk3_lsr {
    


    //查看stat: 状态信息
    public static void ls(String path,ZooKeeper zk, Stat stat) throws Exception {
        //like: "127.0.0.1:3000,127.0.0.1:3001,127.0.0.1:3002/app/a"

        //get--child
        List<String> child = zk.getChildren(path, false, stat);
        byte[] data = zk.getData(path, false, stat);

        System.out.println(path+"  data==>"+new String(data));
        System.out.println(path+"  stat==="+stat);

        if(child !=null)
        for (String s : child) {
            System.out.println("child==="+s);
            
            //遍历子节点
            if(path.equals("/")){
                ls(path+s, zk,  stat);
            }else{
                ls(path+"/"+s, zk,  stat);
            }
        }
        
        //while (true) Thread.sleep(100);
    }


    public static void main(String[] args) throws Exception {
        //设置参数
        String conn="s104:2181,s102:2181,s103:2181";
        ZooKeeper zk = new ZooKeeper(conn, 5000, null);
        Stat stat = new Stat();
        //调用方法

        ls("/", zk,stat);
    }

}
