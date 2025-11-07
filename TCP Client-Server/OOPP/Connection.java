import java.io.*;
import java.net.*;

public class Connection {

    private ServerSocket ss;
    private Socket clientSocket;

    public Connection(int port) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
    }

    public void startServer() throws IOException {
        int clientCount=0;
        System.out.println("Waiting for client...");
        while(true){
        Socket clientSocket = ss.accept();
        clientCount++;
        System.out.println("Client "+clientCount+" connected.");

        ChatHandler handle =new ChatHandler(clientSocket);
        Thread thread =new Thread(handle);
        thread.start();
        }
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (ss != null) {
            ss.close();
        }
        System.out.println("Server closed.");
    }
}
