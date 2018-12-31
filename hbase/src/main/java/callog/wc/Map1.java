package callog.wc;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;

public class Map1 extends Mapper<ImmutableBytesWritable,Result, Text,IntWritable> {

    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        List<Cell> cells = value.listCells();

        for (Cell cell : cells) {
            String line = Bytes.toString(CellUtil.cloneValue(cell));
            String[] splits = line.split(" ");

            for (String split : splits) {


                context.write(new Text(split), new IntWritable(1));
            }

        }
    }
}
