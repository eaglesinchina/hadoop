package mpr.groupSort;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class Comparto1 extends WritableComparator {

    public Comparto1() {
        super(TmpBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
            //先比year    再比tmp
            if(a instanceof TmpBean && b instanceof  TmpBean){
                TmpBean t1 = (TmpBean) a;
                TmpBean t2 = (TmpBean) b;

                if(t1.getYear().equals(t2.getYear())){
                    return -(t1.getTmp()- t2.getTmp());
                }
                return -(t1.getYear().compareTo(t2.getYear()));
            }
            return  0;
        }
}
