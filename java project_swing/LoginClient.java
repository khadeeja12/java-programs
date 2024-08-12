import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginClient {


    public static void main(String[] args) {

        try (Socket cs = new Socket("localhost", 1234);
             DataInputStream sin = new DataInputStream(cs.getInputStream());
             DataOutputStream sout = new DataOutputStream(cs.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {
    
            int ch, age, stat;
            String uname, pass, str, name, gender, add;
    
            while ((ch = menu(scanner)) != 3) {
                switch (ch) {
                    case 1:
                        System.out.print("Enter username : ");
                        uname = scanner.nextLine();
                        System.out.print("Enter password : ");
                        pass = scanner.nextLine();
                        str = "select * from login where username='" + uname + "' and password = '" + pass + "'";
                        sout.writeUTF("select");
                        sout.writeUTF(str);
                        if (sin.readBoolean()) {
                            clearConsole();
                            System.out.println("Welcome " + uname);
                            loginActions(scanner, sin, sout, uname);
                        } else {
                            System.out.println("Wrong Details!!");
                        }
                        break;
                        case 2:
                        clearConsole();
                        System.out.print("Enter your name : ");
                        name = scanner.nextLine();
                        System.out.print("Enter age : ");
                        age = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter gender  : ");
                        gender = scanner.nextLine();
                        System.out.print("Enter address : ");
                        add = scanner.nextLine();
                        System.out.print("Enter phone no : ");
                        long phno = Long.parseLong(scanner.nextLine());
                        System.out.print("Enter username : ");
                        uname = scanner.nextLine();
                        System.out.print("Enter password : ");
                        pass = scanner.nextLine();
                        stat = uname.equals("admin") ? 0 : 1;
                        str = "insert into login values('" + name + "'," + age + ",'" + gender + "','" + add + "'," + phno + ",'" + uname + "', '" + pass + "','" + stat + "')";
                        Class.forName("com.mysql.jdbc.Driver");
                        
                        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", "");
                             PreparedStatement ps = con.prepareStatement(str)) {
                            
                            sout.writeUTF("insert");
                            sout.writeUTF(str);
                            System.out.println("Successfully registered");
                        } 
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    
                }
            }
            sout.writeUTF("quit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private static void loginActions(Scanner scanner, DataInputStream sin, DataOutputStream sout, String uname) throws Exception {
        int mch;
       int userStatus = uname.equals("admin") ? 0 : 1;
        String pass, passnew, str;
        ResultSet rs = null;
        
        try {
            
            while ((mch = loginMenu(scanner, userStatus)) != 5)
             { 
                switch (mch) {
                    case 1: // Change Password
                        System.out.print("Enter old password : ");
                        pass = scanner.nextLine();
                        System.out.print("Enter new password : ");
                        passnew = scanner.nextLine();
                        System.out.print("Re-enter new password : ");
                        String pass2 = scanner.nextLine();
                        str = "select * from login where username='" + uname + "' and password = '" + pass + "'";
                        sout.writeUTF("select");
                        sout.writeUTF(str);
                        if (sin.readBoolean()) {
                            if (passnew.equals(pass2)) {
                                str = "update login set password = '" + passnew + "' where username = '" + uname + "'";
                                sout.writeUTF("insert");
                                sout.writeUTF(str);
                                System.out.println("Password successfully changed");
                            } else {
                                System.out.println("Password mismatch!");
                            }
                        } else {
                            System.out.println("Wrong password!");
                        }
                        break;
                    case 2: // add class
                       if(userStatus==0)
                       {

                        System.out.print("Enter class name: ");
                        String className = scanner.nextLine();
                        System.out.println("enter the date:");
                        String classDate = scanner.nextLine();
                        System.out.print("Enter class time: ");
                        String classTime = scanner.nextLine();
                        str = "insert into class_schedule (class_name,date, time) values ('" + className + "','" + classDate + "', '" + classTime + "')";
                        sout.writeUTF("insert");
                        sout.writeUTF(str);
                        System.out.println("Class added successfully.");
                       }
                       else //book class
                       {
                          
                           System.out.println("Available classes:");
                            
                            sout.writeUTF("pickclass");
                            sout.writeUTF("select * from class_schedule");
                            
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", ""); 
                            Statement st = con.createStatement();
                             rs = st.executeQuery("select * from class_schedule");
                            
                            // Display available classes
                            while (rs.next()) {
                                System.out.println(rs.getString("class_name") + " - " + rs.getString("time"));
                            }
                            
                            
                            System.out.print("Enter the name of the class you want to book: ");
                            String className = scanner.nextLine();
                            
                            
                            boolean classExists = false;
                            rs.beforeFirst(); 
                            while (rs.next()) {
                                if (className.equals(rs.getString("class_name"))) {
                                    classExists = true;
                                    break;
                                }
                            }
                            
                            if (classExists) {
                                // Book the class
                                sout.writeUTF("insert");
                                sout.writeUTF("insert into bookings (username, class_name, book_status) values ('" + uname + "', '" + className + "', 'Booked')");
                                boolean bookingStatus = sin.readBoolean();
                                if (bookingStatus) {
                                    System.out.println("Class booked successfully.");
                                } else {
                                    System.out.println("Failed to book the class. Please try again.");
                                }
                            } else {
                                System.out.println("The entered class does not exist.");
                            }
                            if (st != null) {
                                st.close();
                            }
                            if (con != null) {
                                con.close();
                            }
        }
        
    
           break;

                    case 3: 
                    if (userStatus == 0) { 
                        
                        while (true) {

                            int manageChoice = manageMembersMenu(scanner);
                            switch (manageChoice) {
                      case 1: // View Members
                                    
                                    // Code to view members
                                    if(userStatus==0)
                    {
                    
                        System.out.println(" Members in this Gym");
                    sout.writeUTF("book");
                    sout.writeUTF("select * from bookings");
                  
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", ""); 
                    Statement st = con.createStatement();
                     rs = st.executeQuery("select * from bookings");
                    
                    // Display bookings
                    while (rs.next()) 
                    {
                        System.out.println(rs.getString("username") + " - " + rs.getString("class_name"));
                    }
                    if (st != null) 
                    {
                        st.close();
                    }
                    if (con != null) 
                    {
                        con.close();
                    }
                   }
                                    break;
                            case 2:  // update class
                                
                                System.out.println("Update Class");
                                
                                System.out.print("Enter class name: ");
                                String className = scanner.nextLine();
                                System.out.print("Enter new date: ");
                                String newDate = scanner.nextLine();
                                System.out.print("Enter new time: ");
                                String newTime = scanner.nextLine();
                            
                               
                                sout.writeUTF("updateClass");
                            
                                
                                sout.writeUTF(className);
                                sout.writeUTF(newDate);
                                sout.writeUTF(newTime);
                            
                                
                                boolean succ = sin.readBoolean();
                                if (succ) {
                                    System.out.println("Class updated successfully.");
                                } else {
                                    System.out.println("Failed to update class.");
                                }
                                break;
                            
                                    
                                case 3: // Schedule Attendance
                                    System.out.println("Schedule Attendance");
                                    
                                    
                                System.out.print("Enter name of the user: ");
                               String username = scanner.nextLine();
                             System.out.print("Enter date : ");
                              String date = scanner.nextLine();
                              System.out.print("Enter time: ");
                              String time = scanner.nextLine();
    
   
                             sout.writeUTF("scheduleAttendance");
    
    
                           sout.writeUTF(username);
                            sout.writeUTF(date);
                            sout.writeUTF(time);
    
  
                             boolean success = sin.readBoolean();
                             if (success) {
                             System.out.println("Attendance scheduled successfully.");
                            } else {
                             System.out.println("Failed to schedule attendance.");
                              }    
   

                                    break;
                            case 4: // add Diet
                                    System.out.println("Add Diet");
                                    sout.writeUTF("addDietForClass");
    
  
                                 System.out.print("Enter class name: ");
                                String classnm = scanner.nextLine();
    

                                  sout.writeUTF(classnm);

                                  System.out.print("Enter the diet for users in class " + classnm + ": ");
                                  String diet = scanner.nextLine();
    
   
                                  sout.writeUTF(diet);
    
   
                                 boolean succes = sin.readBoolean();
    
   
                                if (succes) {
                                System.out.println("Diet added successfully for users in class: " + classnm);
                              } else {
                                 System.out.println("Failed to add diet for users in class: " + classnm);
                                }
                                 break;
                                    
                                    
                                case 5: // Logout
                                     return;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                        }
                    
                    }
                     else {
                        return;
                    }
                    
                
                    case 4: // View Bookings
                    if(userStatus==0)
                    {
                    System.out.println("Bookings:");
                    
                    sout.writeUTF("book");
                    sout.writeUTF("select * from bookings");
                  
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", ""); 
                    Statement st = con.createStatement();
                     rs = st.executeQuery("select * from bookings");
                    
                    // Display bookings
                    while (rs.next()) 
                    {
                        System.out.println(rs.getString("username") + " - " + rs.getString("class_name"));
                    }
                    if (st != null) 
                    {
                        st.close();
                    }
                    if (con != null) 
                    {
                        con.close();
                    }
                   }
                      break;
                   case 5:
                        break;      
                }
    
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

        
    



    private static int manageMembersMenu(Scanner scanner) { //manage members menu
        try {
            System.out.println("\n\nManage Members\n1 -> View Members\n2 -> Update Class\n3 -> Schedule Attendance\n4 -> Add Diet\n5 -> Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            return choice;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
    }
    
    private static ResultSet fetchResultSet(DataInputStream sin) throws Exception {
    
    int resultSetLength = sin.readInt();
    
    if (resultSetLength == 0) {
        return null;
    }
    
    byte[] resultSetBytes = new byte[resultSetLength];
    
    sin.readFully(resultSetBytes);
    
    ByteArrayInputStream bis = new ByteArrayInputStream(resultSetBytes);
    ObjectInputStream ois = new ObjectInputStream(bis);
    ResultSet resultSet = (ResultSet) ois.readObject();
    ois.close();
    bis.close();
    return resultSet;
}

    
    private static int menu(Scanner scanner) //starting menu
    {
        int ch = 3;
        try 
        {
            System.out.println("\n\n1 -> Login\n2 -> Register\n3 -> Quit\n");
            System.out.print("Enter your choice : ");
            ch = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ch;
    }

    private static int loginMenu(Scanner scanner, int userStatus) {//login menu
        int ch = 3;
        try 
        {
            
            if (userStatus == 0) 
            { 
                System.out.println("\n\n1 -> Change Password\n2 -> Add Classes\n3 -> Manage Members\n4 -> View Bookings\n5 -> Logout");
            } 
            else 
            { 
                System.out.println("\n\n1 -> Change Password\n2 -> Book Class\n3 -> Logout");
            }
            System.out.print("Enter your choice : ");
            ch = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return ch;
    }
    

    private static void clearConsole() { //clear the console
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
