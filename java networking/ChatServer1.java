import java.io.*;                            //group chat 
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

public class ChatServer1 {

    private static List<ChatSocket> clients = new ArrayList<>();

    public static void main(String[] args) {
        
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ChatSocket chatSocket = new ChatSocket(clientSocket);
                clients.add(chatSocket);

                Thread clientThread = new Thread(new ClientHandler(chatSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private ChatSocket chatSocket;

        public ClientHandler(ChatSocket chatSocket) {
            this.chatSocket = chatSocket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if(chatSocket.messageAvailable()){
                        String message = chatSocket.recieveMessage();
                        if (!message.contains(":") && message.equals("QUIT")) {
                            chatSocket.close();
                            clients.remove(chatSocket);
                            return;
                        }
                        System.out.println(message);
                        broadcastMessage(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void broadcastMessage(String message) {
        for (ChatSocket client : clients) {
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

