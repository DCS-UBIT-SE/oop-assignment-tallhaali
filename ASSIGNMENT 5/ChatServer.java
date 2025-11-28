import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {
    public static ArrayList<PrintWriter> cliePrintWriters = new ArrayList<PrintWriter>();

    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket;

        int port = 9090;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Chat Server has started!");
            try {

                while (true) {
                    clientSocket = serverSocket.accept();
                    handleClients client = new handleClients(clientSocket);
                    client.start();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}

class handleClients extends Thread {
    Socket clientSocket;
    BufferedReader clientReader;
    PrintWriter clientWriter;

    handleClients(Socket socket) {
        try {
            clientSocket = socket;
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            ChatServer.cliePrintWriters.add(clientWriter);
        } 
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void run() {

        System.out.println("Client connected successfully!");
        String chatMessage = null;
        String username = null;
        try {
            clientWriter.println("Please enter your username: ");
            username = clientReader.readLine();

            if (username == null) {
                System.out.println("Client closed connection before providing a username.");
                return;
            }
            System.out.println("Hello " + username + "! Welcome to our chat server!");
            clientWriter.println("Hello " + username + "! Welcome to our chat server!");


            while (true) {
                chatMessage = clientReader.readLine();
                if (chatMessage == null) {
                    // client disconnected abruptly
                    System.out.println("Client " + username + " disconnected (socket closed).");
                    break;
                }
                if (chatMessage.equalsIgnoreCase("quit")) {
                    // client requested to quit
                    System.out.println("Client " + username + " requested to quit.");
                    break;
                }

                for (PrintWriter writer : ChatServer.cliePrintWriters) {
                    writer.println(username + ": " + chatMessage);
                }
            }

        } catch (Exception e) {
            System.out.println("Error handling client" + (username != null ? " (" + username + ")" : "") + ": " + e.toString());
        } finally {
            // ensure writer is removed and socket is closed
            try {
                ChatServer.cliePrintWriters.remove(clientWriter);
            } catch (Exception ignore) {
            }
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException ignore) {
            }
            System.out.println("Cleanup done for client" + (username != null ? (" " + username) : "") + ".");
        }
    }
}
