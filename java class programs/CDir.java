import java.io.*;
import java.util.*;
class CDir
{
public static void main(String args[])
{
File f;
String s[];
int i;
try
{
f=new File("F:\\java Shiju sir");
s=f.list();
for(i=0;i<s.length;i++)
{
System.out.println(s[i]);
}
}
catch(Exception e)
{
}
}
}