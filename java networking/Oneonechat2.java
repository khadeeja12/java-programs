import java.io.*;
import java.net.*;

public class Oneonechat2 {
    public static void main(String args[]) {
        Socket cs;
        DataInputStream sin;
        DataOutputStream sout;
        try {
            cs = new Socket("localhost", 1234);
            sin = new DataInputStream(cs.getInputStream());
            sout = new DataOutputStream(cs.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str;
            while (true) {
                str = sin.readUTF();
                if (str.equals("quit")) {
                    System.out.println("Server has terminated the chat. Exiting...");
                    break;
                }
                System.out.println("Server says: " + str);
                System.out.print("You: ");
                str = reader.readLine();
                sout.writeUTF(str);
                if (str.equals("quit")) {
                    System.out.println("Terminate chat tataa....");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
