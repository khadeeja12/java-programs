import java.io.*;
interface i1
{
void disp();
}
class c1 implements i1
{

public void disp()
{
System.out.println("\n disp c1");
}
}
class c2 implements i1
{
public void disp()
{
System.out.println("\n disp c2");
}	
}
class MInstance
{
public static void main(String args[])
{
i1 obj[]=new i1[3];
obj[0]=new c1();
obj[1]=new c2();
obj[2]=new c1();
for( int i=0;i<3;i++)
{
	if(obj[i] instanceof c1)
	{
		c1 p=(c1)obj[i];
		p.disp();
	}
	else if(obj[i] instanceof c2)
	{
		c2 q=(c2)obj[i];
		q.disp();
	}
}


}
}