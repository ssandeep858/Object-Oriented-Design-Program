package common;

public class AbstractProduct {
    public AbstractProduct() {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void makeFactory(int input) {

        if (input == 1) {
            System.out.println("Factory created for product");
            Product product;

        } else {
            System.out.println("product factory is not created ");
        }


    }
}
