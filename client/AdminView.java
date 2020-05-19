package client;
import java.rmi.RemoteException;
import java.util.Scanner;

import common.Command;
import common.Common;
import common.User;
public class AdminView extends Dispatcher {
    Scanner sc=new Scanner(System.in);
    Common stub;
    User loggedInUserObject;
    public AdminView(Common stub,User loggedInUserObject){

        this.loggedInUserObject=loggedInUserObject;
        this.stub=stub;

    }
    public void show() throws RemoteException {
        System.out.println("Admin view Displaying ");
        System.out.println("Admin ");
        do {


            System.out.println("1.Enter new User \n2.Remove a User \n3.Add Product \n4.Remove Product \n5.Update Quantity&Price of Product  \n6.add another Admin \n7.remove admin \n8.Exit ");
            System.out.println("enter our choice");
           try {
               int choice = sc.nextInt();
               sc.nextLine();


               switch (choice) {
                   case 1:
                       String new_username, new_password, new_email;
                       boolean success2 = false;
                       do {

                           System.out.println("enter user name of new User ");
                           new_username = sc.nextLine();

                           System.out.println("enter email of new user ");
                           new_email = sc.nextLine();

                           System.out.println("enter Password for new user ");
                           new_password = sc.nextLine();
                           User user2 = new User(new_username, new_email, new_password, false);
                           RemoteControl remoteControl=new RemoteControl();
                           Create create=new Create();
                           Command createCommand=new CreateCommand(create);
                           if (stub.admin_add_user(user2)) {
                               System.out.println(" New User Registration successful");
                               success2 = true;
                               System.out.println("new list of users after adding is ");
                               userDisplay(stub.display_users());

                           } else {
                               System.out.println("User already exists \n Pls Try again ");

                           }
                       }
                       while (!success2);
                       break;
                   case 2:
                       boolean success3 = false;
                       do {
                           String remove_email;
                           System.out.println("list of users are ");

                           userDisplay(stub.display_users());
                           System.out.println("enter email id of user that you want to remove ");
                           remove_email = sc.nextLine();

                           if (stub.admin_remove_user(remove_email)) {
                               System.out.println("User Removed Successfully ");
                               success3 = true;
                               System.out.println("updated list is \n ");
                               System.out.println("new list of users after removing is ");
                               userDisplay(stub.display_users());
                           } else {
                               System.out.println("username not found in saved Arraylist \n So enter username that is already registered  ");
                           }
                       }
                       while (!success3);
                       break;
                   case 3:
                       String p_name, type;
                       int new_quantity, n_price, id;
                       try {
                           System.out.println("enter name of new product");
                           p_name = sc.nextLine();
                           System.out.println("enter quantity");
                           new_quantity = sc.nextInt();
                           sc.nextLine();
                           System.out.println("enter type of new product");
                           type = sc.nextLine();
                           System.out.println("enter id of product");
                           id = sc.nextInt();
                           System.out.println("enter price of new product");
                           n_price = sc.nextInt();
                           if (stub.admin_add_product(p_name, new_quantity, type, id, n_price)) {
                               System.out.println("New Product added Successfully");
                               System.out.println("after adding updated list is ");
                               clientDisplay(stub.display());
                           }
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                       break;
                   case 4:
                       System.out.println("enter the id of the product that you want to remove completely ");
                       int remove_id;
                       remove_id = sc.nextInt();
                       if (stub.admin_remove_product(remove_id)) {
                           System.out.println("Product removed Successfully");
                           System.out.println("after deleting items list will look like");

                           clientDisplay(stub.display());
                       } else {
                           System.out.println("id input was wrong");
                       }
                       break;
                   case 5:
                       System.out.println("enter id of product that you want to update ");
                       try {
                           int update_id, updated_quantity, updated_price;
                           update_id = sc.nextInt();
                           System.out.println("List of Products are \n");
                           clientDisplay(stub.display());
                           System.out.println("Enter the updated Quantity ");
                           updated_quantity = sc.nextInt();
                           System.out.println("Enter the updated  Price of the product");
                           updated_price = sc.nextInt();
                           if (stub.admin_update(update_id, updated_quantity, updated_price)) {
                               System.out.println("updation successfull");
                               System.out.println("New Products list is ");
                               clientDisplay(stub.display());
                           }
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                       break;
                   case 6:
                       System.out.println("Now We will add another admin ");
                       boolean s = false;
                       do {
                           String admin_name, admin_pass, admin_email;
                           System.out.println("enter name of new admin ");
                           admin_name = sc.nextLine();
                           System.out.println("enter email of new admin ");
                           admin_email = sc.nextLine();
                           System.out.println("enter password of new admin");
                           admin_pass = sc.nextLine();
                           if (stub.admin_add_admin(admin_name, admin_email, admin_pass, true)) {
                               System.out.println("Admin registered Successfully ");
                               System.out.println("after adding admin list of users will look like ");
                               userDisplay(stub.display_users());
                               s = true;
                           }
                       }
                       while (!s);
                       break;

                   case 7:
                       System.out.println("Now We will remove another admin ");
                       boolean s2 = false;
                       do {
                           String admin_email;
                           System.out.println("enter email of  admin ");
                           admin_email = sc.nextLine();
                           if (stub.admin_remove_admin(admin_email)) {
                               System.out.println("Admin removed Successfully ");
                               System.out.println("after  removing admin list of users will look like ");
                               userDisplay(stub.display_users());
                               s2 = true;
                           }
                       }
                       while (!s2);
                       break;

                   case 8:
                       System.out.println("exiting and logging out admin and closing client side ");
                       System.exit(0);
                   default:
                       System.out.println("wrong choice try again \n ");
               }
           }
           catch(Exception E) {
               System.out.println("input mismatch");
           }

        }while(true);

    }
}
