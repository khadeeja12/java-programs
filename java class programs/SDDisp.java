import java.io.*;
import java.util.*;
class SDDisp
{
public static void main(String args[])
{
File f;
String s[];
int i;
try
{
f=new File("F:\\project\\");
s=f.list();
for(i=0;i<s.length;i++)
{
File ff=new File("F:\\project\\"+s[i]);
if(ff.isDirectory())
{
System.out.println(s[i]);
}
}
}
catch(Exception e)
{
}
}
}