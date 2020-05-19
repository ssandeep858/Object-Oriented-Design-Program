package client;
import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

import common.Command;
import common.Common;
import common.Product;
import common.User;

public class FrontControllerPattern {
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

    public static void main(String[] args)  {

        try {

            Scanner sc = new Scanner(System.in);

            String host = args[0];
            String port = args[1];
            String name = "//" + host + ":" + port + "/RemoteAccount";
            Common stub = (Common) Naming.lookup(name);
            RemoteControl remoteControl=new RemoteControl();
            //creating a command create to add new user
            Create create=new Create();
            Command createCommand=new CreateCommand(create);
            //calling new user create command
            String username, email, password;
            int input;
            do {
            System.out.println("1.Register   \n2.Login    \n3.Exit");
            System.out.println("enter your choice ");
                input = sc.nextInt();
                sc.nextLine();
                switch (input) {
                    case 1:

                        boolean success = false;
                        do {

                            System.out.println("enter user name ");
                            username = sc.nextLine();

                            System.out.println("enter email ");
                            email = sc.nextLine();

                            System.out.println("enter Password ");
                            password = sc.nextLine();
                            User user = new User(username, email, password, false);

                            if (stub.register(user)) {
                                System.out.println("Registration successful");
                                remoteControl.setCommand(createCommand);
                                remoteControl.pressButton();
                                success = true;

                            } else {
                                System.out.println("User already exists \n Pls Try again ");
                                sc.nextLine();
                            }
                        }
                        while (!success);
                        break;
                    case 2:
                        boolean login = false;
                        User loggedInUserObject = null;
                        do {


                            System.out.println("Enter Name  ");
                            username = sc.nextLine();
                            System.out.println("enter email ");
                            email = sc.nextLine();

                            System.out.println("enter Password ");
                            password = sc.nextLine();

                            User user = new User(username, email, password, false);

                            loggedInUserObject = stub.login(user);
                            if (loggedInUserObject != null) {
                                System.out.println("login successful");
                                FrontController frontController = new FrontController(stub, loggedInUserObject);
                                // FrontController Object being created adn common stub being passed along with user
                                login = true;
                                int choice;
                                if (loggedInUserObject.isAdmin) {
                                    System.out.println("Admin Credentials found \n Do you want to act as customer and buy items or \nYou want to perform admin responsibilities");
                                    System.out.println("1.Buy items as normal customer \n2.Perform Admin responsibilities");
                                    System.out.println("enter your choice ");

                                    choice = sc.nextInt();
                                    if (choice == 1) {
                                        System.out.println("Registering new user " + stub.register(user));
                                        System.out.println("new list of users after adding is ");
                                        userDisplay(stub.display_users());

                                        frontController.dispatchRequest(1);  // for customer value 1 is passed
                                    } else {

                                        frontController.dispatchRequest(2);  // for admin value 2 is passed in dispatching request
                                    }
                                }else
                                    frontController.dispatchRequest(1);
                            }
                        }
                        while (!login);
                        break;
                    case 3:
                        System.out.println("exiting the client side ");
                        System.exit(0);
                    default:
                        System.out.println("Wrong choice ");
                }
            }while(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }