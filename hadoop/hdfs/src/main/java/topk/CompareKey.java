package topk;

public class CompareKey implements  Comparable<CompareKey> {
    //属性
    private int sum;
    private String tmp;

    //构造
    public CompareKey(int sum, String tmp) {
        this.sum = sum;
        this.tmp = tmp;
    }

    public CompareKey() {
    }

    //set,get
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

//排序。。。。
    public int compareTo(CompareKey o) {
       if(sum==o.sum){
           return tmp.compareTo(o.tmp);
       }

       return o.sum-sum;
    }
}
