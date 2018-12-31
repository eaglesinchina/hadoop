package b;

import java.util.List;

public class User {

    //字段
    private int id;
    private int age;
    private String name;
    private String sex;

    private List<Orders> listOrders;

    //构造
    public User(int id ,int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.id=id;
    }
    public User(int age, String name, String sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
        this.id=id;
    }
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", id=" + id +
                ", list="+getListStr() +
                '}';
    }
    public String getListStr(){
        String res="";
        for (Orders listOrder : listOrders) {
            res+=listOrder.toString()+"| ";
        }

        return res;
    }

    //set,get
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Orders> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<Orders> listOrders) {
        this.listOrders = listOrders;
    }
}
