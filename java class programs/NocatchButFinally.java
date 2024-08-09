import java.io.*;
class NocatchButFinally
{
public static void main(String args[])
{
try
{
int c;
c=5/0;
System.out.println(c);
}
finally
{
System.out.println("here");
}
}
}
