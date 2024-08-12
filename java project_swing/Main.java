import java.io.*;
import java.util.*;
import java.sql.*;

public class Main{
    public static void main(String args[]) throws Exception
    {
        Connection con;
        Class.forName("oracle.jdbc.driver.OracleDriver");
         con=DriverManager.getConnection("jdbc:oracle:thin@localhost:1521:orcl","system","1234");
         CallableStatement st=con.prepareCall("{call usrinsert(?,?,?)}");
         st.setString(1,"mca");
         st.setString(2,"north");
         st.execute();
         System.out.println("Success");
        
    }
}
