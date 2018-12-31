package mr_sort.json_store_info_sort.mpr;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class Red2 extends Reducer<Text,Text,  Text,Text> {
    StringBuilder sb = new StringBuilder();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {


        /**
         * 拼接字符串
         */


        //统计：75144086	   效果赞_2"    评论总数

        for (Text value : values) {
            sb.append(value.toString()).append(", ");
        }

        String info = sb.toString().substring(0,sb.length()-1);

        context.write(key, new Text(info));
        context.write(new Text(" "), new Text("\n"));
    }
}
