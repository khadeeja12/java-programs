
import java.sql.*; 
import java.util.Scanner; 
public class StoredProcedure  
{ 
public static void main(String[] args) throws SQLException  
{ 
// TODO Auto-generated method stub 
try  
{ 
Class.forName("oracle.jdbc.driver.OracleDriver"); 
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
 "system","1234"); 
Scanner sc=new Scanner(System.in); 
System.out.println("Enter department no:"); 
String dno=sc.nextLine(); 
System.out.println("Enter department name:"); 
String dname=sc.nextLine(); 
System.out.println("Enter department location:"); 
String dloc=sc.nextLine(); 
CallableStatement cs=con.prepareCall("{call insert_department(?,?,?)}"); 
cs.setString(1, dno); 
cs.setString(2, dname); 
cs.setString(3, dloc); 
cs.executeQuery(); 
System.out.println("Inserted successfully"); 
 

 
}  
catch (ClassNotFoundException e)  
{ 
e.printStackTrace(); 
} 
} 
} 