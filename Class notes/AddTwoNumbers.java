import java.io.*;
public class AddTwoNumbers
{
 public static void main(String args[])
{
 int a,b;
 DataInputStream din;
 din=new DataInputStream(System.in);
try
{
 System.out.println("Enter the first number:");
 a=Integer.parseInt(din.readLine());
System.out.println("Enter the second number:");
 b=Integer.parseInt(din.readLine());
System.out.println("Sum of " +a+ "and " +b+ "is " + (a+b));
}
catch(Exception e)
{
System.out.println("Error" +e);
}
}
}