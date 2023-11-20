package productsapplication;

import java.sql.*;
public class DButility {
    
    public DButility () {
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        String db_url = "jdbc:mysql://localhost:3306/ProductsDB_AlHothly_AlMadi";
        String user = "AA";
        String pass = "123456";
        
        Connection conn = DriverManager.getConnection(db_url, user, pass);
        System.out.println("Connection Establish");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
        public static void main (String args[]) {
        DButility con = new DButility();
        }
    
}
