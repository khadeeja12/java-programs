import java.io.*;
import java.util.*;
import java.sql.*;

public class Main{
    public static void main(String args[]) throws Exception
    {
        Connection con;
        
        ResultSet rs=null;
        
         Class.forName("com.mysql.jdbc.Driver");
         con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","");
         CallableStatement st=con.prepareCall("{call usrinsert(?,?,?)}");
         st.setString(1,"mca");
         st.setString(2,"msc");
         st.execute();
         System.out.println("Success");
        
    }
}
