package newapi;

import kafka.utils.VerifiableProperties;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

public class MyPartition implements Partitioner {

    //必填项，不然无法通过配置文件配置
    public MyPartition( ) {
    }

    public int partition(String s, Object o, byte[] bytes,    Object o1, byte[] bytes1, Cluster cluster) {
        //根据Key分区
        return Math.abs(o.hashCode()) %cluster.partitionCountForTopic("t3");
    }

    public void close() {

    }

    public void configure(Map<String, ?> map) {

    }
}
