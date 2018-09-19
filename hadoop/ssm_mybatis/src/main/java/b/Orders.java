package b;

import java.util.List;

/**
 * id | price | user_id | product
 */
public class Orders {
    private  int id;
    private User user;
    private List<Product> listProduct;

    //构造
    public Orders(int id, User user, List<Product> listProduct) {
        this.id = id;
        this.user = user;
        this.listProduct = listProduct;
    }
    public Orders() {
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", user=" + user +
                ", listProduct=" + getListStr() +
                '}';
    }
    public String getListStr(){
        String res="";
        for (Product p : listProduct) {
            res+=p.toString()+"=== ";
        }

        return res;
    }
    //set,get

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct = listProduct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
