package threadTest.heshang2;

public class Heshang extends Thread{
    //属性
    int totalEat;
    Manager man;
    String mes="";

    //方法
    public Heshang( Manager man) {
        this.man=man;
    }

    @Override
    public void run() {
        while (true){

            int foodNum = man.couldEeatorNot(this);

            //线程结束时：　打印消费信息(总消费数, 具体消费详单)
            if (foodNum ==-1) {
                System.out.println(getName()+"  "+totalEat + "----｜"+mes);
                break;
            }

            else{
                //消费时：　记录消费详情
                mes+=foodNum+",";
                totalEat++;
            }
        }
    }
}
