package client;
import java.util.List;
import java.util.Scanner;

import common.Common;
import common.Product;
import common.User;


public class CustomerView extends Dispatcher{

    Scanner sc=new Scanner(System.in);
    Common stub;
    User loggedInUserObject;

    public CustomerView(Common stub,User loggedInUserObject)
    {

        this.stub=stub;
        this.loggedInUserObject=loggedInUserObject;

    }

    public static void clientDisplay(List<Product> productList) {
        System.out.println("ID|NAME |QUANTITY|TYPE|PRICE ");
        System.out.println("------------------------------");
        for (Product product : productList) {
            System.out.println(product.toString());
        }
    }

    public static void userDisplay(List<User> userList) {
        System.out.println("NAME|EMAIL|PASSWORD|ADMIN ");
        System.out.println("------------------------------");
        for (User user : userList) {
            System.out.println(user.toString());
        }
    }
    public void show() throws Exception {

        System.out.println(" Displaying Customer view ");
        while (true) {
            System.out.println("Items in the store are as follows ");

            clientDisplay(stub.display());
            System.out.println("Which one do you want to purchase? Enter its id or enter -1 to check your cart: ");
            int productId = sc.nextInt();
            if (productId == -1)
                break;
            System.out.println("Enter quantity: ");
            int productQty = sc.nextInt();


            try {

                for (Product product : stub.add_to_Cart(loggedInUserObject, productId, productQty)) {
                    System.out.println("Items selected by user are");
                    System.out.println(product.toString());
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        int i;
        System.out.println("Do you want to purchase  the items then press 1 else 2 to empty your cart ");
        i = sc.nextInt();

        if (i == 1) {
            System.out.println("order completed and purchased with amount " + stub.processed_Amount(loggedInUserObject));
            System.out.println("Updated list of items after purchasing are ");
            clientDisplay(stub.display());

        } else {

            System.out.println("cart is discarded");
        }

    }
}
