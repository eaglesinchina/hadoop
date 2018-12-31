package consumer_producer.newapi;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class NewProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "slave1:9092,slave2:9092,slave3:9092");
        props.put("acks", "all");
        props.put("group.id","g1");

        props.put("partitioner.class","topic_partition.patitioin.MyPartition2");

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>("t3", Integer.toString(i), Integer.toString(i)));
            System.out.println("key--->"+Integer.toString(i)+"\t hash==>"+Integer.toString(i).hashCode());
        }
        producer.close();
    }
}
