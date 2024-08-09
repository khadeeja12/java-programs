import java.io.*;
class CThread implements Runnable
{
 int m,n;
 public CThread(int a,int b)
 {
 m=a;
 n=b;
}
public void run()
{
 while(m!=n)
 {
 System.out.println("\t" +m);
 if(m<n)
     m++;
 else
    m--;
 }
 System.out.println("\t"+m);
}
}
class MThreadIm
{
public static void main(String args[])
{
int m,n;
CThread t1,t2;
Thread p,q;
DataInputStream din=new DataInputStream(System.in);
try
{
System.out.println("Enter first number:");
m=Integer.parseInt(din.readLine());
System.out.println("Enter second number:");
n=Integer.parseInt(din.readLine());
t1= new CThread(m,n);
t2= new CThread(n,m);
p=new Thread(t1);
q=new Thread(t2);
p.start();
q.start();
}
catch(Exception e)
{
 System.out.println("Error:" +e);
}

}
}
