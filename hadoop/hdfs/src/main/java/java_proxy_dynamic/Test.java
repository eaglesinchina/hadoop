package java_proxy_dynamic;

public class Test {

    public static void main(String[] args) throws Throwable {
        //原生目标： 效果
        System.out.println("Target_educator ***************************");
        Target_educator tar=new Target_educator();
        tar.shareKnowledge();


        //通过代理实现： 目标效果
        System.out.println("\n\n");
        System.out.println("Proxy_educatorManager  ***************************");
        new Proxy_school(tar).newEdutor().shareKnowledge();
    }

}
