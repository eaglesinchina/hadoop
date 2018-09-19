package mpr_join2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReduceJoinReducer extends Reducer<CompKey, Text, Text, NullWritable> {

    @Override
    protected void reduce(CompKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Iterator<Text> iter = values.iterator();

        //iter.hasNext();
        //用户行
        Text cusLine = iter.next();
        //取出用户信息L：名字:1
        String[] cus = cusLine.toString().split("\t");
        String cusname=cus[1];

        while (iter.hasNext()) {

            //order行
            String orderLine = iter.next().toString();
            String[] ordInfo = orderLine.split("\t");

            //取出订单信息：  订单号1， 价格2
            String ordnum=ordInfo[1];
            String price=ordInfo[2];

            //拼串操作，并将其输出
            String msg=ordnum+"\t"+cusname+"\t"+price;
            context.write(new Text(msg),NullWritable.get());
        }


    }
}
