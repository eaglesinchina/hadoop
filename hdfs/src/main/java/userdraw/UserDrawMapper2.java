package userdraw;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class UserDrawMapper2 extends Mapper<LongWritable, Text, Text, CompKey> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        String[] arr = line.split("\\|");

        String phone = arr[0];

        CompKey ck = new CompKey(arr[1], Integer.parseInt(arr[2]));

        context.write(new Text(phone), ck);


    }
}
