import java.io.*;
class Complex
{
double r;
double i;
public void read()
{
	DataInputStream din=new DataInputStream(System.in);
	try
	{
	System.out.println("Enter the real part:");
    r=Double.parseDouble(din.readLine());
    System.out.println("Enter the imaginary part:");
    i=Double.parseDouble(din.readLine());	
	}
	catch(Exception e)
	{
		System.out.println("Error:"+e);
	}
}
public void disp()
{
	System.out.println(r+"+"+i+"i");
}
public Complex add(Complex q)
{
Complex t= new Complex();
t.r=r+q.r;
t.i=i+q.i;
return t;
}
public Complex mul(Complex q)
{
Complex t= new Complex();
t.r=r*q.r-i*q.i;
t.i=r*q.i-i*q.r;
return t;
}
}
 class MComplex
{
	public static void main(String args[])
	{
		Complex p,q,r;
		p=new Complex();
		q=new Complex();
		p.read();
		q.read();
		r=p.add(q);
		p.disp();
		q.disp();
		r.disp();
		r=p.mul(q);
		r.disp();
	}
}