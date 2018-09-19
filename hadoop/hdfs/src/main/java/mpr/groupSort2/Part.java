package mpr.groupSort2;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class Part extends Partitioner<TmpBean,NullWritable> {
    public int getPartition(TmpBean tmpBean, NullWritable nullWritable, int numPartitions) {

        //相同年份---一个分区
//        return Integer.parseInt(tmpBean.getYear())% numPartitions;
        int has=(tmpBean.getYear().hashCode()& Integer.MAX_VALUE) %numPartitions;
        return has;
    }
}
