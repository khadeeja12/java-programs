import java.io.*;
import java.util.*;
class NCWL
{
public static void main(String args[])
{
FileInputStream fin;
String fname;
int i=0,w=0,c=0,l=1;
try
{
Scanner s=new Scanner(System.in);
System.out.println("Enter the file name:");
fname = s.nextLine();
fin = new FileInputStream(fname);
byte b[];
b=new byte[fin.available()];
fin.read(b);
String data=new String(b,0,b.length);
while(i<data.length())
{
if(data.charAt(i)==' '|| data.charAt(i)=='\n')
{
w=w+1;
if(data.charAt(i)=='\n')
{
l=l+1;
}
}
else
{
c=c+1;
}
i++;
}
System.out.println("No of character="+c);
System.out.println("No of words="+w);
System.out.println("No of lines="+l);
}
catch(Exception e)
{
}
}
}

