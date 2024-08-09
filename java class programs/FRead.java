import java.util.*;
import java.io.*;
class FRead
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
int ch;
ch=fin.read();
while(ch!=-1)
{
System.out.println((char)ch);
ch=fin.read();
}
fin.close();
}
catch(Exception e)
{
System.out.println("Error:"+e);
}
}
}