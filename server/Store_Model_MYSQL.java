package server;
import java.sql.*;
import java.util.*;
import common.Product;
import common.User;
public class Store_Model_MYSQL {

        private static Store_Model_MYSQL store_modelMYSQL = new Store_Model_MYSQL();
        //creating singleton instance of model class so that every time no new instances is created instead old instance is worked upon

        public List<Product> products;
        public List<User> users;
        public Map<User,List<Product>> cart;
        public Connection connection;

        private Store_Model_MYSQL()
        {
            products = new ArrayList<>();
            users = new ArrayList<>();
            cart =new HashMap<>();
        }
        //show all values already in databases
        public void preprocessDB() throws SQLException{
            databaseDisplay_Product();
            databaseDisplay_User();
        }
        public static Store_Model_MYSQL getInstance() throws SQLException {
            return store_modelMYSQL;
        }
    //initialising Connection connection from mysql main class to model class via this function
        public void setConnection(Connection connection) throws SQLException {
            this.connection=connection;
        }
        //displays all users
        public void databaseDisplay_User() throws SQLException {
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery("SELECT * from user");
            while(rs.next()){
                users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)));
            }
            System.out.println("MYSQL User updated table is also displayed ");
        }
        public void databaseDisplay_Product() throws SQLException {
            Statement st = connection.createStatement();
            ResultSet pd=st.executeQuery("SELECT * from product");
            while(pd.next()){
                products.add(new Product(pd.getString(1),pd.getInt(2),pd.getString(3),pd.getInt(4),pd.getInt(5)));
            }
            System.out.println("MYSQL Product updated table is also displayed ");
        }
        public List<User> getUserList(){
            return users;
        }
        public Map<User,List<Product>> getCart(){
            return cart;
        }
        public boolean putCart(User user,List<Product> products)
        {
            cart.put(user, products);
            return true;
        }

      //admin adding every new functionalities under this
    public void addAdmin(User user) throws SQLException {

        PreparedStatement ps =connection.prepareStatement("INSERT INTO  user(name,email,password,isAdmin) VALUES (?,?,?,?);");
        ps.setString(1,user.name);
        ps.setString(2,user.email);
        ps.setString(3,user.password);
        ps.setBoolean(4,user.isAdmin);
        int status=ps.executeUpdate();
        if(status!=0)
        {
            System.out.println("MYSQL DATABASE OUTPUT :ADMIN  was inserted");
        }

    }

        public boolean addUser(User user) throws SQLException {
            users.add(user);
            PreparedStatement ps =connection.prepareStatement("INSERT INTO  user(name,email,password,isAdmin) VALUES (?,?,?,?);");
            ps.setString(1,user.name);
            ps.setString(2,user.email);
            ps.setString(3,user.password);
            ps.setBoolean(4,user.isAdmin);
            int status=ps.executeUpdate();
            if(status!=0)
            {
                System.out.println("MYSQL DATABASE OUTPUT : User  was inserted");
            }

            return true;
        }
         public boolean removeUser(User user) throws SQLException {
            if(users.remove(user)) {
                PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE email= ? ");
                ps.setString(1, user.email);
                int status=ps.executeUpdate();
                if(status!=0)
                {
                    System.out.println("MYSQL DATABASE OUTPUT :  User was removed   ");
                }
            } return true;
        }
        public boolean removeProduct(int i) throws SQLException {
            products.remove(i);
            PreparedStatement ps =connection.prepareStatement("DELETE FROM product WHERE id= ? ");
            ps.setInt(1,i);
            int status=ps.executeUpdate();
            if(status!=0)
            {
                System.out.println("MYSQL DATABASE OUTPUT :  Product was removed   ");
            }
                return true;
        }
        public boolean updateProduct(int i , Product product) throws SQLException {
            products.set(i, product);
            PreparedStatement ps =connection.prepareStatement("UPDATE product  SET quantity = ? , price = ? WHERE id= ? ");
            ps.setInt(1, product.quantity);
            ps.setInt(2, product.price);
            ps.setInt(3, product.id);
            int status=ps.executeUpdate();
            if(status!=0)
            {
                System.out.println("MYSQL DATABASE OUTPUT :  Product details in table was updated  ");
            }
            return  true;
        }

        public boolean updateProductQty(int i, Product product) throws SQLException{
            products.set(i, product);
            PreparedStatement ps =connection.prepareStatement("UPDATE product  SET quantity = ? WHERE id= ? ");
            ps.setInt(2, product.id);
            ps.setInt(1, product.quantity);
            int status=ps.executeUpdate();
            if(status!=0)
            {
                System.out.println("MYSQL DATABASE OUTPUT :After shopping  Product  table was updated  ");
            }
            return  true;
        }

        public List<Product> getProductList()
        {
            return products;
        }

        public boolean addProduct(Product product) throws SQLException
        {
            products.add(product);
            int status;
            PreparedStatement ps =connection.prepareStatement("INSERT INTO  product(name,quantity,type,id,price) VALUES (?,?,?,?,?);");
            ps.setString(1, product.name);
            ps.setInt(2, product.quantity);
            ps.setString(3, product.type);
            ps.setInt(4, product.id);
            ps.setInt(5, product.price);
            status=ps.executeUpdate();
            if(status!=0)
            {
                System.out.println("MYSQL DATABASE OUTPUT :New Product was inserted");
            }
            return true;
        }
}