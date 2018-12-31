package isolation;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaBenchMark2 {
    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        int count=1024 * 1024/3;

        for (int i = 1;i<=3; i++) {

            int start1=(i-1)*count;
            int end1=i*count;

            //创建线程， 生产数据
            new Thread1(start1,end1).start();

        }
        System.out.println(System.currentTimeMillis() - start);

    }
}
