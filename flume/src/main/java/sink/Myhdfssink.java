package wang;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Myhdfssink extends AbstractSink implements Configurable {

    //信息-->写入Hdsf
    String default_path="/1.txt";
    String custom_path="";

    public void configure(Context context) {
        //获取配置
        custom_path=context.getString("path", default_path);
    }


    public Status process() throws EventDeliveryException {

        Status result = Status.READY;
        Channel channel = getChannel();
        Transaction transaction = channel.getTransaction();
        Event event = null;

        try {
            transaction.begin();
            event = channel.take();

            //取出数据----》写入数据
            String body = new String(event.getBody());

            FileSystem fs = FileSystem.get(new Configuration());

            Path path = new Path(custom_path);
            FSDataOutputStream out=null;
            if(fs.exists(path)){
                 out = fs.append(path);

            }else {
                 out = fs.create(path, false);
            }

            out.write(body.getBytes());
            out.write("\n".getBytes());
        
            //关闭
            out.close();
            System.out.println("event  发送成功....");

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            throw new EventDeliveryException("Failed to log event: " + event, ex);
        } finally {
            transaction.close();
        }


        return result;
    }
}
