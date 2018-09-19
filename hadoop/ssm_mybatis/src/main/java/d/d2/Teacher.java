package d.d2;

import d.Husband;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private int id;
    private String name;
    private List<Student> students =new ArrayList<Student>();

    public Teacher(String name) {
        this.name = name;
    }
    public Teacher() {
    }

    public void add(Student...stus){
        for (Student student : stus) {
            this.students.add(student);
            student.getTeachers().add(this);
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
