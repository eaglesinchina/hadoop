package d.d2;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private List<Teacher> teachers=new ArrayList<Teacher>();


    public Student(String name) {
        this.name = name;
    }
    public Student() {
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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
}
