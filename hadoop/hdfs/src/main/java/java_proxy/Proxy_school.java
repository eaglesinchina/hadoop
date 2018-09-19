package java_proxy;

public class Proxy_school {
    private Target_educator edu=new Target_educator();

    public void shareKnowledge(){

        System.out.println("开学典礼....分班..提供食宿...");
        edu.shareKnowledge();
        System.out.println("成绩考核...发证书..毕业典礼..");
    }
}
