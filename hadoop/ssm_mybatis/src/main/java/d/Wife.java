package d;

public class Wife {
    private int id;
    private String name;
    private Husband husband;


    public Wife( String name,Husband husband) {
        this.name = name;
        this.husband=husband;
        husband.setWife(this);
    }

    public Wife() {
    }


    public Husband getHusband() {
        return husband;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
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
}
