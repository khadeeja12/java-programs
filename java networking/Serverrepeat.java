import java.io.*;
import java.net.*;

public class Serverrepeat {
    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String receivedMessage;
            while (true) {
                receivedMessage = dis.readUTF();
                if (receivedMessage.equals("quit")) {
                    System.out.println("Client has terminated the chat. Exiting...");
                    break;
                }
                System.out.println("Client: " + receivedMessage);

                System.out.print("You: ");
                String sendMessage = reader.readLine();
                dos.writeUTF(sendMessage);
                if (sendMessage.equals("quit")) {
                    System.out.println("You have terminated the chat. Exiting...");
                    break;
                }
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
