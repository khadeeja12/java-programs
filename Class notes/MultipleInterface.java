import java.io.*;
interface i1
{
void disp();
}
interface i2
{
void print();
}
class c1 implements i1,i2
{

public void disp()
{
System.out.println("\n disp i1");
}
public void print()
{
System.out.println("\n print i1");
}
}
class MultipleInterface
{
public static void main(String args[])
{
c1 obj =new c1();
obj.disp();
obj.print();
}
}