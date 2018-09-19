package wang;

import org.apache.flume.Channel;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.event.EventHelper;
import org.apache.flume.sink.AbstractSink;

import java.util.Map;
import java.util.Set;

public class Mysink  extends AbstractSink {


    public Status process() throws EventDeliveryException {


        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();

            //取出event数据
            String body = new String(event.getBody());
            Map<String, String> map = event.getHeaders();

            Set<Map.Entry<String, String>> entries = map.entrySet();
            String head="head--> ";

            for (Map.Entry<String, String> entry : entries) {
                head+=entry.getKey()+","+entry.getValue();
            }

            head+="\t body---> "+ body;
            System.out.println("event===>"+head);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }
return  result;

    }
}
