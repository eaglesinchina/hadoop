package hadoop.comm.mpr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Red11 extends Reducer<Text,IntWritable,  Text,IntWritable> {

   TreeMap<String,Integer> map=new TreeMap<String,Integer>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        //输入---一行字符:  求出现最多的单词

        int sum=0;
        for (IntWritable value : values) {
            sum+=value.get();
        }

        //判断最大值
//        if(sum>max){
//            max=sum;
//        }
       map.put(key.toString(), sum);

        //输出最大值
//        if(! context.nextKey()){
//            context.write(new Text(key.toString()), new IntWritable(sum));
//        }
    }


    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        TreeMap<String,Integer> map2=new TreeMap<String,Integer>();
        //排序： 以数字开头
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            //拼接： new key-->val+key
            key=value+key;
            map2.put(key, value);
        }


        context.write(new Text(map2.firstKey()), new IntWritable(map.get(map2.firstKey())));
//        context.write(new Text(map2.lastKey()), new IntWritable(map.get(map2.lastKey())));
    }
}
