package common;

import java.io.Serializable;


public class Product implements Serializable {
    public String name;
    public int quantity;
    public String type;
    public int id;
    public int price;

    public Product(String name, int quantity, String type, int id, int price) {
                this.name=name;
                this.quantity=quantity;
                this.type=type;
                this.id=id;
                 this.price=price;
    }

    @Override
    public String toString() {
        return this.id + ". " + this.name + " " + this.quantity + " " + this.type+" " +this.price;
    }

}












