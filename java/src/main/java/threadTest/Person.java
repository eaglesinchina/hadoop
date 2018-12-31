package threadTest;


import org.junit.Test;

/**
 * age, height, weight, blood -->hashcode()
 */
public class Person {

    private int age, height, weight, blood;

    public Person(int age, int height, int weight, int blood) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.blood = blood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;

        return person.hashCode()== hashCode();
    }

    @Override
    public int hashCode() {
        //  a, b, c ,d   : 4* 8 (3*8,   2*8, 8, 0)
        int hash1 = age << 24;
        int hash2 = height << 16;
        int hash3 = weight << 8;
        int hash4 = blood;

        return hash1|hash2|hash3|hash4;

    }
    
    






    public static void main(String[] args) {

        Person p = new Person(23, 2345, 3454, 435);
        Person p2 = new Person(23, 2345, 3454, 435);
        Person p22 = new Person(23, 7775, 3454, 435);


        System.out.println(p.hashCode() +", "+p22.hashCode() );
        System.out.println(p.equals(p2));
        System.out.println(p.equals(22));
    }
}
