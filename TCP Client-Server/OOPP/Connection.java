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
    int clientCount = 0;
    System.out.println("Waiting for client...");
    while(true){
       Socket clientSocket = ss.accept();  
        clientCount++;
        System.out.println("Client "+clientCount+" connected.");
         DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

        // Authenticate client
        Authentication auth = new Authentication();
        auth.sendKey(clientSocket);

        if (!auth.verification(clientSocket)) {
    
    dos.writeBoolean(false); // notify client verification failed
    clientSocket.close();
    System.out.println("Client authentication failed. Connection closed.");
    continue; // wait for next client
} else {
       dos.writeBoolean(true); // notify client verification successful
}

    
        ChatHandler chat = new ChatHandler(clientSocket, clientCount);
Thread thread = new Thread(chat);
thread.start();

    }
}
}
