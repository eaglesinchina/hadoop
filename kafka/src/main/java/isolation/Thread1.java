package isolation;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Thread1 extends Thread {

    Properties props = new Properties();
    private int start, end;
    static int totalTime=0;

    public Thread1(int start, int end) {
        props.put("bootstrap.servers", "slave1:9092,slave2:9092,slave3:9092");
        props.put("acks", "0");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
    }

    @Override
    public void run() {
        //写入数据
        long start = System.currentTimeMillis();
        Producer<String, Integer> producer = new KafkaProducer<String, Integer>(props);
        for (int i = this.start; i < end; i++) {
            ProducerRecord<String, Integer> record = new ProducerRecord<String, Integer>("t12", i);

            try {
                //异步状态
                producer.send(record).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }//for

        //关闭资源
        producer.close();
//        totalTime+=(System.currentTimeMillis()-start);
        System.out.println(System.currentTimeMillis()-start);
    }
}
