import java.io.*;
class Person
{
	int age;
	String name;
   public void read()
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
   public void disp()
   {
	System.out.println("Name:"+name+" Age:"+age);
   }
}
class Teacher extends Person{
String subj;
public void read()
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
public void disp()
   {
	System.out.println("Suhbject:"+subj);
   }
}

public class Override
{
	public static void main(String args[])
	{
		Person p;
		Teacher t;
		p=new Person();
		t=new Teacher();
		p.read();
		t.read();
		p.disp();
		t.disp();
	}
}