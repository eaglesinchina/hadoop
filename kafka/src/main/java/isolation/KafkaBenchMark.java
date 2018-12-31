package isolation;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaBenchMark {
    public static void main(String[] args) throws Exception {
        Properties props = new Properties();
        props.put("bootstrap.servers", "slave1:9092,slave2:9092,slave3:9092");
        props.put("acks", "0");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

        long start = System.currentTimeMillis();
        Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
        for (int i = 0; i < 1024 * 1024; i++) {
            ProducerRecord<String, byte[]> record = new ProducerRecord<String, byte[]>("t12", new byte[1024]);
            //异步状态
            producer.send(record).get();

        }
        System.out.println(System.currentTimeMillis() - start);
        producer.close();
    }
}
