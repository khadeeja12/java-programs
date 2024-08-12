import java.io.*;
import java.net.*;
import java.sql.*;

public class LoginServer{
    
    public static void main(String[] args) {
        Connection con;
        Statement st;
        String str;
        ServerSocket ss;
        Socket as;
        DataInputStream sin;
        DataOutputStream sout;
        ResultSet rs;
        try {
            ss = new ServerSocket(1234);
            as = ss.accept();
            sin = new DataInputStream(as.getInputStream());
            sout = new DataOutputStream(as.getOutputStream());

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8","root","");
            st = con.createStatement();

            while(true){
                str = sin.readUTF();
                if(str.equals("insert")) //insertion
                {
                    str = sin.readUTF();
                    System.out.println(str);
                    st.executeUpdate(str);
                }
                if(str.equals("select"))//selection
                {
                    str = sin.readUTF();
                    System.out.println(str);
                     rs = st.executeQuery(str);
                    if(rs.next())
                        sout.writeBoolean(true);
                    else
                        sout.writeBoolean(false);
                }
                if (str.equals("pickclass")) {
                    
                    String query = sin.readUTF();
                    System.out.println(query); 
                
                    
                     rs = st.executeQuery(query);
                
                    boolean hasRows = rs.next();
                
                    
                    sout.writeBoolean(hasRows);
                }
                
                if (str.equals("book")) {
                   
                    String query = sin.readUTF();
                    System.out.println(query); 
                
                    
                     rs = st.executeQuery(query);
                
               
                    boolean hasRows = rs.next();
                
                    
                    sout.writeBoolean(hasRows);
                
                }
                
                
                

                if (str.equals("updateclass")) {//update class
                    
                    String className = sin.readUTF();
                    String newDate = sin.readUTF();
                    String newTime = sin.readUTF();
                    
                    
                    boolean success = updateclass(className, newDate, newTime);
                    
                   
                    sout.writeBoolean(success);
                }


                if (str.equals("scheduleAttendance")) { //add attendance
                    
                    String username = sin.readUTF();
                    String date = sin.readUTF();
                    String time = sin.readUTF();
                    
                    boolean success = scheduleAttendance(username, date,time);
                    
                    
                    sout.writeBoolean(success);
                }
                
                
                if (str.equals("addDietForClass")) { //add diet
                    
                    String className = sin.readUTF();
                    String diet = sin.readUTF();
                    
                    boolean success = addDietForClass(className,diet);
                    
             
                    sout.writeBoolean(success);
                }
                
                if(str.equals("quit")){
                    ss.close();
                    break;
                }
            }
        }catch (Exception e) {
            System.out.println(e);
        }
    }

    private static boolean updateclass(String className, String newDate, String newTime) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", "");
            PreparedStatement pstmt = con.prepareStatement("UPDATE class_schedule SET date = ?, time = ? WHERE class_name = ?");
            pstmt.setString(1, newDate);
            pstmt.setString(2, newTime);
            pstmt.setString(3, className);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean scheduleAttendance(String username, String date,String time) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", "");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO attendance (username, date,time) VALUES (?, ?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, date);
            pstmt.setString(3, time);
            int rowsAffected = pstmt.executeUpdate();
            pstmt.close();
            con.close();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean addDietForClass(String className,String diet) {
        try {
            // Establish database connection
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym_management?characterEncoding=utf8", "root", "");
            
            
            PreparedStatement pstmt = con.prepareStatement("SELECT username FROM bookings WHERE class_name = ?");
            pstmt.setString(1, className);
            ResultSet rs = pstmt.executeQuery();
            
            
            while (rs.next()) {
                String username = rs.getString("username");
                PreparedStatement insertStmt = con.prepareStatement("INSERT INTO diet (username, class_name, diet) VALUES (?, ?, ?)");
                insertStmt.setString(1, username);
                insertStmt.setString(2, className);
                insertStmt.setString(3, diet);
                insertStmt.executeUpdate();
                insertStmt.close();
            }
            
            // Close resources
            rs.close();
            pstmt.close();
            con.close();
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}