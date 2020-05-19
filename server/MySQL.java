package server;

import java.rmi.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import common.Common;


public class MySQL {

    public static void main(String args[]) {
        try {
            String host = args[0];
            String port = args[1];
            Common remote_obj = new Store_Controller();
            //establishing a connection with port number 7777
             Connection connection =null;
             String username="Admin";
            String password="";

            String name = "//"+host+":"+port+"/RemoteAccount";
            Naming.rebind(name, remote_obj);

            System.out.println("server is ready");

                //loading driver for jdbc
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:7777/Store?useSSL=false", username, password);
                Statement st = connection.createStatement();
                System.out.println("Connection established successfully ");
                Store_Model_MYSQL store_model_mysql = Store_Model_MYSQL.getInstance();
                store_model_mysql.setConnection(connection);
                store_model_mysql.preprocessDB();
                // see all values that are already stored in databases.
                ResultSet userTable = st.executeQuery("SELECT * FROM user");
                System.out.println("DISPLAYING MYSQL DATA INITIALLY FROM USER TABLE \n ");
                System.out.println("NAME|EMAIL|PASSWORD|ADMIN ");
                System.out.println("------------------------------");
                while (userTable.next()) {

                    System.out.println(userTable.getString(1) + " " + userTable.getString(2) + " " + userTable.getString(3) + " " + userTable.getBoolean(4));

                }
                System.out.println(" \n DISPLAYING MYSQL DATA INITIALLY FROM PRODUCTS TABLE ");
                System.out.println("ID|NAME |QUANTITY|TYPE|PRICE ");
                System.out.println("------------------------------");
                ResultSet productTable=st.executeQuery("SELECT * FROM product");
                while(productTable.next()){
                    System.out.println(productTable.getString(1)+" "+productTable.getInt(2) + " " +productTable.getString(3)+ " " + productTable.getInt(4) +" "+ productTable.getInt(5));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}




