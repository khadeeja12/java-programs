import java.io.*;
import java.net.*;

public class Server {
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
            String str = sin.readUTF();
            System.out.println("client says: " + str);
            System.in.read();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
