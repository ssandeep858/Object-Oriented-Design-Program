package common;

import java.rmi.*;
import java.sql.SQLException;
import java.util.List;

public interface Common extends Remote{
    Boolean register(User user) throws RemoteException, SQLException;
    User login(User user)throws RemoteException;
    List<Product> display()throws RemoteException;
    List<User> display_users()throws RemoteException;
    List<Product> add_to_Cart(User user, int id, int quantity)throws Exception;
    int processed_Amount(User user)throws Exception;
    boolean admin_add_user(User user) throws RemoteException, SQLException;
    boolean admin_remove_user(String remove_email) throws RemoteException, SQLException;
    boolean admin_add_product(String name, int amount, String type, int id, int price) throws RemoteException, SQLException;
    boolean admin_remove_product(int id) throws RemoteException, SQLException;
    boolean admin_update(int id,int amount,int price) throws RemoteException, SQLException;
    boolean admin_add_admin(String name,String email,String password , boolean isAdmin) throws RemoteException, SQLException;
    boolean admin_remove_admin(String email) throws RemoteException, SQLException;

}
