/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package productsapplication;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBconnection {

    Connection con;
    ResultSet Rset;
    PreparedStatement ps;
    Scanner input = new Scanner(System.in);
    char UserDel;
    boolean constatue;

    DBconnection() throws FileNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties config = new Properties(); //taking the properties from config file
            FileInputStream ip = new FileInputStream("config.txt"); //to read config file
            config.load(ip); //load it, to put inside con
            con = DriverManager.getConnection(config.getProperty("DBUrl"), config.getProperty("user"), config.getProperty("password"));
            //con = DriverManager.getConnection("jdbc:mysql://localhost/productsdb_alhothly_almadi", "root", "AboOody32147896");
            System.out.println("Connection Established!!");
            constatue = true;//if the connection established start the program normally
        } catch (FileNotFoundException e) {// if the file not found
            System.out.println("Error: " + e.getMessage());//inform the user
            constatue = false;// don't let the program start

        } catch (Exception e) {// if there any problem with connection
            System.out.println("Error in connection: " + e.getMessage());//inform the user
            constatue = false;// don't let the program start
        }
    }
    //to close the connection
    public void close() throws Exception {
        ps.close();
        con.close();
        System.out.println("Connection Closed!!");
    }

    //inserting some records to database
    public void insert(String Type, String Model, float Price, int Count, String DeliveryDate) throws SQLException {
        try {
            ps = con.prepareStatement("INSERT INTO ProductsTBL_Azzam_Abdulmalik(Type,Model,Price,Count,DeliveryDate) VALUES(?,?,?,?,?)");// sql code
            ps.setString(1, Type);//putting the Type from parameter into first argument in sql code
            ps.setString(2, Model);
            ps.setFloat(3, Price);
            ps.setInt(4, Count);
            ps.setString(5, DeliveryDate);
            ps.executeUpdate();// execute
            System.out.println(" The data added successfully!!");
        } catch (SQLException e) {//if there's any error (ex. Type exceeds 20 char) it will show error
            System.out.println("Error: " + e.getMessage());
        }
        ps.close();
    }

    public void search(String MT) throws SQLException {
        try {
            boolean found = false;
            ps = con.prepareStatement("SELECT * FROM ProductsTBL_Azzam_Abdulmalik WHERE Model LIKE ? OR Type LIKE ?");
            ps.setString(1, "%" + MT + "%");
            ps.setString(2, "%" + MT + "%");

            ResultSet Rset = ps.executeQuery();
            //to print the table
            System.out.printf("--------------------------------------------------------------------------------------------------------%n");//104
            System.out.printf("                                            PRODUCTS TABLE                                                    %n");//44
            System.out.printf("--------------------------------------------------------------------------------------------------------%n");
            System.out.printf("| %-2s | %-20s | %-40s | %-8s | %-5s | %-10s | %n", "ID", "TYPE", "MODEL", "PRICE", "COUNT", "DATE");
            System.out.printf("--------------------------------------------------------------------------------------------------------%n");
            //using while loop to get all records from database according to searching criteria.
            while (Rset.next()) {
                int id = Rset.getInt("ID");
                String Type = Rset.getString("Type");
                String Model = Rset.getString("Model");
                float price = Rset.getFloat("Price");
                int count = Rset.getInt("Count");
                String dd = Rset.getString("DeliveryDate");
                
                System.out.printf("| %-2d | %-20s | %-40s | %-8.2f | %-5d | %-4s | %n", id, Type, Model, price, count, dd);
                found = true; //if he found the records change it to true
            }
            // to check if the records not found it will print meesage
            if (!found) {
                System.out.println("No data match");
            }

            System.out.printf("--------------------------------------------------------------------------------------------------------%n");

            Rset.close();
            ps.close();
        } catch (SQLException se) {// to detect any sql error
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //delete an element from database
    public void delete(int ID) throws SQLException {// delete method
 
        ps = con.prepareStatement("SELECT * FROM ProductsTBL_Azzam_Abdulmalik WHERE ID = ?");// select all the record based on the ID
        ps.setInt(1, ID);

        Rset = ps.executeQuery();

        if (Rset.next()) {// if this record was found enter the if condtiion
            System.out.println("Are you sure you want to delete this record? (Y/N)");//here we ask the user to make sure he want to delete the correct record
            UserDel = input.next().charAt(0);//if he write 'Yes' or 'No' just take the first letter
            UserDel = Character.toUpperCase(UserDel); // change the character to upperCase so we can handle it

            if (UserDel == 'Y') {//if the user type Y that's mean this is the correct record that the user want
                ps = con.prepareStatement("DELETE FROM ProductsTBL_Azzam_Abdulmalik WHERE ID = ?");// go to database and remove that record
                ps.setInt(1, ID);// ID number will go to the First parameter
                ps.executeUpdate();
                System.out.println("Successfully deleted");
            } else if (UserDel == 'N') {
                System.out.println("Delete operation cancelld");

            }

        } else {
            System.out.println("Sorry record not found.");// if the record not found we will go back to the menu
        }
        ps.close();
        Rset.close();
    }

}
