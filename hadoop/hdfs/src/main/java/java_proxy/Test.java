package java_proxy;

public class Test {

    public static void main(String[] args) {
        //原生目标： 效果
        System.out.println("Target_educator ***************************");
        new Target_educator().shareKnowledge();


        //通过代理实现： 目标效果
        System.out.println("\n\n");
        System.out.println("Proxy_school  ***************************");
        new Proxy_school().shareKnowledge();
    }

}
