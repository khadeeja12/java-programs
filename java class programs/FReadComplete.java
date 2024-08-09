import java.util.*;
import java.io.*;
class FReadComplete
{
public static void main(String args[])
{
FileInputStream  fin;
Scanner s=new Scanner(System.in);
String fname;
try
{
System.out.println("Enter the file name:");
fname=s.nextLine();
fin=new FileInputStream(fname);
byte []b;
b=new byte[fin.available()];
fin.read(b);
String data=new String (b,0,b.length);
System.out.println(data);
fin.close();
}
catch(Exception e)
{
System.out.println("Error:"+e);
}
}
}