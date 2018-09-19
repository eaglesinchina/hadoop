package day02.homework.heshang2;

public class Manager {

    //属性
    int noteatedPeople = 30;
    int foodCount = 100;

    //方法： 是否发放馒头【是-->return 馒头编号, 否-->return -1】
    public int couldEeatorNot(Heshang peo) {

        //判断
        //1,不发
        if (foodCount < 0) {
            return -1;

        } else if (peo.totalEat == 4) {
            return -1;
        }

        //2,发食物
        else {

            //第一次吃
            if (peo.totalEat == 0) {
                noteatedPeople--;
            }

            //临界消费点：　手动分配
            if(foodCount == noteatedPeople){
                    if(peo.totalEat >0) {
                        return -1;//过滤：　已吃的人
                    }
            }
            return foodCount --;
        }
    }
}
