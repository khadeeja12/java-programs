import java.io.*;
import java.net.*;

public class Clientlala {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 1234);
            DataInputStream sin = new DataInputStream(socket.getInputStream());
            DataOutputStream sout = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            while (true) {
                String received = sin.readUTF();
                if (received.equals("quit")) {
                    System.out.println("Server has terminated the chat. Exiting...");
                    break;
                }
                System.out.println("Server says: " + received);
                System.out.print("You: ");
                String message = reader.readLine();
                sout.writeUTF(message);
                if (message.equals("quit")) {
                    System.out.println("Terminate chat. Goodbye!");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
