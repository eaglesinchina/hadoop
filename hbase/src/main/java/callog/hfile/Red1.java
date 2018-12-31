package callog.hfile;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red1 extends Reducer<Text,IntWritable, ImmutableBytesWritable,Cell> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //取出单词频数： 相加求和
        int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();

        }
        //(byte[] row, byte[] family, byte[] qualifier, long timestamp, byte type, byte[] value
        context.write(new ImmutableBytesWritable(Bytes.toBytes(key.toString())),

                CellUtil.createCell(Bytes.toBytes("row1"),
                        Bytes.toBytes("f1"),
                        Bytes.toBytes("count") ,

                        System.currentTimeMillis(),
                        KeyValue.Type.Minimum,
                        Bytes.toBytes(sum+""),
                        null
                ));

        //createCell(byte[] row, byte[] family, byte[] qualifier, long timestamp, Type type, byte[] value, byte[] tags)
    }
}
