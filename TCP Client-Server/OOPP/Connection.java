import java.io.*;
import java.net.*;

public class Connection {

    private ServerSocket ss;

    public Connection(int port) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
    }

    public void startServer() throws IOException {
        int clientCount = 0;
        System.out.println("Waiting for client...");

        while (true) {
            Socket clientSocket = ss.accept();
            clientCount++;
            System.out.println("Client " + clientCount + " connected.");

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            
            Authentication auth = new Authentication();
            auth.sendKey(dos);
            if (!auth.verification(dis)) {
                dos.writeBoolean(false);
                clientSocket.close();
                System.out.println("Client " + clientCount + " authentication failed. Connection closed.");
                continue;
            }

            dos.writeBoolean(true);
            System.out.println("Client " + clientCount + " authenticated successfully.");

            
            String mode = dis.readUTF();

            if (mode.equalsIgnoreCase("chat")) {
                ChatHandler chat = new ChatHandler(clientSocket, clientCount);
                new Thread(chat).start();
            } else if (mode.equalsIgnoreCase("file")) {
                FileTransferHandler fileHandler = new FileTransferHandler(clientSocket, clientCount);
                new Thread(fileHandler).start();
            } else {
                System.out.println("Invalid mode from Client " + clientCount);
                clientSocket.close();
            }
        }
    }
}
