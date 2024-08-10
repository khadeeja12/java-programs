import java.io.*;                                       //multiple clients here chating to server
import java.net.*;

public class MultiClientServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            DataInputStream sin = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream sout = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            sout.writeUTF("Welcome to the chat!");

            while (true) {
                String received = sin.readUTF();
                if (received.equals("quit")) {
                    System.out.println("Client " + clientSocket + " has terminated the chat.");
                    break;
                }
                System.out.println("Client " + clientSocket + " says: " + received);
                System.out.print("You: ");
                String message = reader.readLine();
                sout.writeUTF(message);
                if (message.equals("quit")) {
                    System.out.println("You have terminated the chat.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
