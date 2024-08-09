import java.io.*;
import java.util.*;
class Copy2File
{
public static void main(String args[])
{
FileInputStream fin1,fin2;
FileOutputStream fout;
Scanner s=new Scanner(System.in);
String sfname1,sfname2,dfname;
try
{
System.out.println("Enter the source file1 name:");
sfname1=s.nextLine();
fin1=new FileInputStream(sfname1);
System.out.println("Enter the source file2 name:");
sfname2=s.nextLine();
fin2=new FileInputStream(sfname2);
System.out.println("Enter the destination file name:");
dfname=s.nextLine();
fout=new FileOutputStream(dfname);
int n=fin1.read();
while(n!=-1)
{
fout.write(n);
n=fin1.read();
}
fout.write("\n".getBytes());
n=fin2.read();
while(n!=-1)
{
fout.write(n);
n=fin2.read();
}
fout.close();
fin1.close();
fin2.close();
}
catch(Exception e)
{
System.out.println("Error:"+e);
}
}
}