package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.*;

import common.Common;
import common.Product;
import common.User;



public class Store_Controller extends UnicastRemoteObject implements Common {

    Store_Model_MYSQL store_modelMYSQL = Store_Model_MYSQL.getInstance();

    Store_Controller() throws RemoteException, SQLException {
        super();

    }


    public Boolean register(User user) throws SQLException {
        System.out.println("adding name remotely into server " + user.name + " and " + user.email);

        for (User user1 : store_modelMYSQL.getUserList()) {
            if (user.email.equals(user1.email)&& !user1.isAdmin)
                return false;
        }
        System.out.println("registering user");
        store_modelMYSQL.addUser(user);

        return true;

    }

    @Override
    public User login(User user) throws RemoteException {

        for (User user1 : store_modelMYSQL.getUserList()) {
            if (user1.email.equals(user.email) && user1.password.equals(user.password))
                return user1;
        }
        System.out.println("try again User doesn't exist");
        return null;
    }

    @Override
    public List<Product> display() throws RemoteException {
        System.out.println("server sending products to client recursively until user terminates  \n");
        return store_modelMYSQL.getProductList();
    }

    @Override
    public List<User> display_users() throws RemoteException {
        System.out.println("returning list of users ");
        return store_modelMYSQL.getUserList();
    }

    @Override
    public List<Product> add_to_Cart(User user, int id, int quantity) throws Exception {
        Product foundProduct = null;
        for (Product product : store_modelMYSQL.getProductList()) {
            if (product.id == id) {
                foundProduct = product;

                break;
            }
        }

        if (foundProduct == null)
            throw new Exception("wrong id entered by the user ");
        if (foundProduct.quantity < quantity) {
            throw new Exception("More quantity requested than available Please try again ");
        }

        List<Product> userProducts = store_modelMYSQL.getCart().get(user);
        if (userProducts == null)
            userProducts = new ArrayList<>();
        userProducts.add(new Product(foundProduct.name, quantity, foundProduct.type, foundProduct.id, foundProduct.price));
        store_modelMYSQL.putCart(user, userProducts);
        return userProducts;
    }

    @Override
    public int processed_Amount(User user) throws Exception {
        List<Product> cartProducts = store_modelMYSQL.getCart().get(user);
        int sum = 0;
        for (Product cartProduct : cartProducts) {//current items in cart
            sum += (cartProduct.quantity * cartProduct.price);
            for (int i = 0; i < store_modelMYSQL.getProductList().size(); i++) {
                //Product product = products.remove(i);


                if (cartProduct.id == store_modelMYSQL.getProductList().get(i).id) {
                    int tempQty = store_modelMYSQL.getProductList().get(i).quantity - cartProduct.quantity;
                    if (tempQty < 0) {
                        throw new Exception("More quantity requested than available after cumulative sum so list was restored back to normal");
                    }
                    store_modelMYSQL.getProductList().get(i).quantity = tempQty;
                    store_modelMYSQL.updateProductQty(i,store_modelMYSQL.getProductList().get(i));
                   // break;
                }
            }
        }
        return sum;
    }

    @Override
    public boolean admin_add_user(User user) throws RemoteException, SQLException {
        System.out.println("adding name remotely into server " + user.name + " and " + user.email);

        for (User user1 : store_modelMYSQL.getUserList()) {
            if (user.email.equals(user1.email))
                return false;
        }

        store_modelMYSQL.addUser(user);
        System.out.println("Adding user ");
        return true;
    }


    @Override
    public boolean admin_remove_user(String remove_email) throws RemoteException, SQLException {
        for (User user1 : store_modelMYSQL.getUserList()) {
            if (remove_email.equals(user1.email)) {

                store_modelMYSQL.removeUser(user1);
                return true;
            }
        }
        System.out.println("USER ID NOT FOUND ");
            return false;

    }

    @Override
    public boolean admin_add_product(String name, int quantity, String type, int id, int price) throws RemoteException, SQLException {
        Product newproduct=new Product(name, quantity, type, id, price);
        store_modelMYSQL.addProduct(newproduct);
        System.out.println("Adding product");
        return true;
    }

    @Override
    public boolean admin_remove_product(int id) throws RemoteException, SQLException {
        for (int i = 0; i< store_modelMYSQL.getProductList().size(); i++)
        {
            if(id== store_modelMYSQL.getProductList().get(i).id)
            {
                store_modelMYSQL.removeProduct(i);
                System.out.println("removes products");
                return true;
            }
        }
        System.out.println("item id not found");
        return false;
    }

    @Override
    public boolean admin_update(int id ,int quantity, int price) throws RemoteException, SQLException {
        for(int i = 0; i< store_modelMYSQL.getProductList().size(); i++)
        {
                if(id== store_modelMYSQL.getProductList().get(i).id)
                {
                    Product updated_Abstract_product =new Product(store_modelMYSQL.getProductList().get(i).name,quantity, store_modelMYSQL.getProductList().get(i).type,id,price);
                    store_modelMYSQL.updateProduct(i, updated_Abstract_product);
                    System.out.println("updating the product details");
                    return true;
                }
        }
    return false;
    }

    @Override
    public boolean admin_add_admin(String name, String email, String password, boolean isAdmin) throws RemoteException, SQLException {
        User user=new User(name,email,password,isAdmin);
        store_modelMYSQL.getUserList().add(new User(name,email,password,isAdmin));
        store_modelMYSQL.addAdmin(user);
        System.out.println("    Adding admin ");
        return true;
    }

    @Override
    public boolean admin_remove_admin(String email) throws RemoteException, SQLException {
        for (User user1 : store_modelMYSQL.getUserList()) {
            if (email.equals(user1.email)) {

                store_modelMYSQL.removeUser(user1);
                return true;
            }
        }
                System.out.println("email not found try again  ");
                return false;

        }
    }



