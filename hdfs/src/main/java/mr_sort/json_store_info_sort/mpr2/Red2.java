package mr_sort.json_store_info_sort.mpr2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.TreeSet;

public class Red2 extends Reducer<Text,ComparKey, Text,Text> {
    @Override
    protected void reduce(Text key, Iterable<ComparKey> values, Context context) throws IOException, InterruptedException {


        /**
         * 拼接字符串
         */


        //统计：75144086,   {效果赞 1}
        StringBuilder sb = new StringBuilder();
        TreeSet<ComparKey> set = new TreeSet<ComparKey>();

        for (ComparKey value : values) {
           set.add(value);
        }

        for (ComparKey s: set) {
            sb.append(s.getContent()).append("_").append((s.getCount())).append(", ");
        }
        String info = sb.toString().substring(0,sb.length()-1);


        context.write(key, new Text(info));
        context.write(new Text(" "), new Text("\n"));
        set.clear();

    }


}
