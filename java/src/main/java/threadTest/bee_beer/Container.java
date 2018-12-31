package threadTest.bee_beer;

/**
 * 蜜蜂：100 --》生成力： 1
 *
 * 容器：  50
 *
 * 熊： 2  ---》消费力： >=20吃光： 容器的蜜
 *
 */
public class Container  {
    //属性： 50 的容量
    public static int capa =50;
    private int count=0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
