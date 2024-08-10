import java.io.*;
import java.net.*;

public class Clientrepeat {
    public static void main(String args[]) {
        try {
            Socket socket = new Socket("localhost", 1234);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String receivedMessage;
            while (true) {
                System.out.print("You: ");
                String sendMessage = reader.readLine();
                dos.writeUTF(sendMessage);
                if (sendMessage.equals("quit")) {
                    System.out.println("Terminate chat. Bye!");
                    break;
                }

                receivedMessage = dis.readUTF();
                if (receivedMessage.equals("quit")) {
                    System.out.println("Server has terminated the chat. Exiting...");
                    break;
                }
                System.out.println("Server: " + receivedMessage);
            }
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
