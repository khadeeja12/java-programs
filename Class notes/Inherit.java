import java.io.*;
class Person
{
	int age;
	String name;
   public void readp()
   {
	DataInputStream din=new DataInputStream(System.in);
	try
	{
	System.out.println("Enter the name and age:");
    name=din.readLine();
    age=Integer.parseInt(din.readLine());	
	}
	catch(Exception e)
	{
		System.out.println("Error:"+e);
	}
   }
   public void dispp()
   {
	System.out.println("Name:"+name+" Age:"+age);
   }
}
class Teacher extends Person{
String subj;
public void readt()
{
	DataInputStream din=new DataInputStream(System.in);
	try
	{
	System.out.println("Enter subject:");
    subj=din.readLine();	
	}
	catch(Exception e)
	{
		System.out.println("Error:"+e);
	}
}
public void dispt()
   {
	System.out.println("Suhbject:"+subj);
   }
}

public class Inherit
{
	public static void main(String args[])
	{
		Person p;
		Teacher t;
		p=new Person();
		t=new Teacher();
		p.readp();
		t.readp();
		t.readt();
		p.dispp();
		t.dispp();
		t.dispt();
	}
}