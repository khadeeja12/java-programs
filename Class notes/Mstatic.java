import java.io.*;
class c1
{
public static void disp()
{
System.out.println("\n disp c1");
}
}
class c2
{
public static void disp()
{
System.out.println("\n disp c2");
}
}
class Mstatic
{
public static void disp()
{
System.out.println("\n disp");
}
public static void main(String args[])
{
disp();
c1.disp();
c2.disp();
}
}
