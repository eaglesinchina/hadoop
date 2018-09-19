package day02.homework.heshang;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Manager {

    //管理：　线程
    ArrayList<Heshang> list=new ArrayList<Heshang>();


    //管理方法
    public void manage(){
        for (Heshang t : list) {


                if (t.eatCount ==4){
                    t.interrupt();
                }else{
                    System.out.println( t.getName() +"没有消费");
                }
                    


        }
    }
}
