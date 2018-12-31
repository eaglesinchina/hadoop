package nio.ref;

import java.io.Serializable;

public class Student extends Per implements Serializable {
    //属性
    private int score;

    //构造
    public Student(int score) {
        this.score = score;
    }
    public Student() {
        System.out.println("student 无参构造");
    }

    //get, set
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
