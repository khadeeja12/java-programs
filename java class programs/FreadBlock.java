import java.util.*;
import java.io.*;
class FReadBlock
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
b=new byte[5];
int n=fin.read(b,0,5);
while(n!=-1)
{
String data=new String (b,0,n);
System.out.println(data);
n=fin.read(b,0,5);
}
fin.close();
}
catch(Exception e)
{
System.out.println("Error:"+e);
}
}
}