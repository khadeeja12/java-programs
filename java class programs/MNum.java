import java.io.*;
class Num
{
int a;
public void read()
{
	DataInputStream din=new DataInputStream(System.in);
	try
	{
	System.out.println("Enter value:");
    a=Integer.parseInt(din.readLine());	
	}
	catch(Exception e)
	{
		System.out.println("Error:"+e);
	}
}
public void disp()
{
	System.out.println("a="+a);
}
public Num add(Num q)
{
Num t=new Num();
t.a=a+q.a;
return t;
}
}
 class MNum
{
	public static void main(String args[])
	{
		Num p,q,r;
		p=new Num();
		q=new Num();
		p.read();
		q.read();
		r=p.add(q);
		p.disp();
		q.disp();
		r.disp();
	}
}