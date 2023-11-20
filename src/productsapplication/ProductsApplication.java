
package productsapplication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class ProductsApplication {

    public static void main(String[] args) throws Exception {
        int choice;
        float Price = 0;
        int Count = 0;
        int ID = 0;
        String DeliveryDate;
        boolean correctFormat = false;
        DBconnection con = new DBconnection();//establish the connect
        Scanner input = new Scanner(System.in);
        if (con.constatue == true){// if the there's error in the connection show the error, and don't run the program
        do {
            System.out.println("Azzam(442003546)_Abdulmalik(442005927) (G1)");
            System.out.println("------Menu------");
            System.out.println("1 INSERT");
            System.out.println("2 SEARCH");
            System.out.println("3 DELETE");
            System.out.println("4 EXIT");
            System.out.println("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:// choice number 1 "inserting an element"
                    System.out.println("Enter The Type: (such as: TV, Refrigerator, or PC)");
                    input.nextLine(); //to get rid of newLine that came from nextLine
                    String Type = input.nextLine();

                    System.out.println("Enter The Model: (such as: Samsung QN90B QLED)");
                    String Model = input.nextLine();

                    System.out.println("Enter The Price: ");
                    do {//to make sure he enters only numbers
                        try {
                            Price = input.nextFloat();
                        } catch (InputMismatchException e) {
                            System.out.println("Please write correct type (Float T  ype): ");
                        }
                        input.nextLine(); // clears the buffer
                    } while (Price <= 0);

                    System.out.println("Enter The Count: ");
                    do {//to make sure he enters only numbers
                        try {
                            Count = input.nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Please write correct type(Integer Type): ");
                        }
                        input.nextLine(); // clears the buffer
                    } while (Count <= 0);

                    System.out.println("Enter The DeliveryDate (YYYY-MM-DD)");
                    do {
                        DeliveryDate = input.next();
                        try {// to make sure the date entering in the right format
                        LocalDate.parse(DeliveryDate,DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT)); //to make sure that date be in this format ("YYYY-MM-DD")
                            correctFormat = true; // if it's right, then change the condition, so it's can get from the loop
                        } catch (DateTimeParseException e) {
                            System.out.println("Invalid date");
                            correctFormat = false; // if not, then let him in the loop
                        }
                    } while (!correctFormat);
                    con.insert(Type, Model, Price, Count, DeliveryDate); // send these parameter to the insert method to insert it to the table
                    break;

                case 2:
                    System.out.println("Enter The Model or Type to search in table: (Press enter to display all data)");
                    input.nextLine(); //to get rid of newLine that came from nextLine
                    String MT = input.nextLine().toUpperCase(); //to look for model or type
                    con.search(MT);
                    break;

                case 3:
                    System.out.println("Enter The ID That You Want To Delete It: ");
                    do {
                        try {
                            ID = input.nextInt();
                            con.delete(ID);// send the ID to the delete method to delete a tuble or record from the database by the id
                        } catch (InputMismatchException e) {
                            System.out.println("Please write the id in integer form: ");
                        }
                        input.nextLine(); // clears the buffer
                    } while (ID <= 0);


                    break;
                case 4:
                    con.close();//close the connection
                    System.out.println("finishing…");
                    choice = 0;//this to end the loop
                    break;

            }

        } while (choice != 0);
    }
    }
}
