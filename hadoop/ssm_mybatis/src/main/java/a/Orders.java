package a;

/**
 * id | price | user_id | product
 */
public class Orders {
    private  int id;
    private  double price;
    private User user;
    private  String product;

    //构造


    public Orders(int id, double price, User user, String product) {
        this.id = id;
        this.price = price;
        this.user = user;
        this.product = product;
    }

    public Orders() {
    }

    //set,get

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", price=" + price +
                ", user=" + user +
                ", product='" + product + '\'' +
                '}';
    }
}
