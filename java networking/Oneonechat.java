import java.io.*;                              // server and client one to one chat.
import java.net.*;

public class Oneonechat {
    public static void main(String args[]) {
        ServerSocket ss;
        Socket as;
        DataInputStream sin;
        DataOutputStream sout;
        try {
            ss = new ServerSocket(1234); // Server socket creation for port no:5001
            as = ss.accept(); // Accept client connection
            sin = new DataInputStream(as.getInputStream());
            sout = new DataOutputStream(as.getOutputStream());
            sout.writeUTF("welcome client"); // Send message to the client
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String str;
            while (true) {
                str = sin.readUTF();
                if (str.equals("quit")) {
                    System.out.println("Client has terminated the chat. Exiting...");
                    break;
                }
                System.out.println("client says: " + str);
                System.out.print("You: ");
                str = reader.readLine();
                sout.writeUTF(str);
                if (str.equals("quit")) {
                    System.out.println("Terminate chat tataa....");
                    break;
                }
            }
            ss.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
