import java.util.*;
class MyEx extends Exception
{
	String strerror;
	public MyEx(String s)
	{
		super();
		strerror=s;
	}
	public void disp()
	{
		System.out.println("Error:"+strerror);
	}
}
class UDEDemo
{
public static void main(String args[])
{
int a,b,c;
Scanner s=new Scanner(System.in);
try
{
System.out.println("Enter the first no:");
a=s.nextInt();
System.out.println("Enter the second no:");
b=s.nextInt();
if(b==0)
{
	throw new MyEx("Dr is zero not possible");
}
c=a/b;
System.out.println("c="+c);
}
catch(MyEx m)
{
m.disp();
}
catch(Exception e)
{
	System.out.println(e);
}
}
}