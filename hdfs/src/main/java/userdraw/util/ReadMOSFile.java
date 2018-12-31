package userdraw.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.util.HashMap;
import java.util.Map;

public class ReadMOSFile {

    static Map<String, String[]> map = new HashMap<String, String[]>();
    static ConfUtil confUtil = new ConfUtil();

    public static Map readMOS() {

        try {

            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", confUtil.filesystem);

            FileSystem fs = FileSystem.get(conf);

            String file = confUtil.mos + "-r-00000";

            Path p = new Path(file);

            FSDataInputStream fis = fs.open(p);

            String line = null;

            while ((line = fis.readLine()) != null) {
                String[] arr = line.split("\t");
                String phone = arr[0];

                String[] arr2 = arr[1].split("\\|");

                //male
                map.put(phone, arr2);
            }
            fis.close();

            fs.delete(p, true);

        } catch (Exception e) {
        }
        return map;

    }

}
