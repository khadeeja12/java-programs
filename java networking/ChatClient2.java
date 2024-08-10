import java.io.*;
import java.net.*;
import java.util.*;

class ChatSocket {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ChatSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream()); 
        this.out = new DataOutputStream(socket.getOutputStream()); 
    }

    String recieveMessage() throws IOException{
        return in.readUTF();
    }

    void sendMessage(String message) throws IOException{
        out.writeUTF(message);
    }

    boolean messageAvailable() throws IOException{
        return in.available() > 0;
    }

    void close() throws IOException{
        this.socket.close();
    }
}

public class ChatClient2 {
    public static void main(String[] args) {
    try {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String username = "";
        System.out.println("Enter username : ");
        username = bufferedReader.readLine();
        ChatSocket chatSocket = new ChatSocket(new Socket("localhost", 8080));
        chatSocket.sendMessage(username + " joined");
        while (true) {
            if (chatSocket.messageAvailable()) {
                String receivedMessage = chatSocket.recieveMessage();
                if (!receivedMessage.contains(":") && receivedMessage.equals("QUIT")) {
                    chatSocket.close();
                    return;
                }
                System.out.println(receivedMessage);
            }

            if (bufferedReader.ready()) {
                String message = bufferedReader.readLine();
                if (message.equals("QUIT")) {
                    chatSocket.sendMessage("QUIT");
                    chatSocket.close();
                    return;
                }
                chatSocket.sendMessage(username + ": " + message);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
