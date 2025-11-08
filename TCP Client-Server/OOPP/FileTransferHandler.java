import java.io.*;
import java.net.*;

public class FileTransferHandler implements Runnable {

    private Socket clientSocket;
    private int clientId;

    public FileTransferHandler(Socket clientSocket, int clientId) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try {
            System.out.println("Starting file transfer for Client " + clientId);

            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());

            File currentDir = new File(".");
            String[] files = currentDir.list();

            if (files != null) {
                dos.writeInt(files.length);
                for (String fileName : files) {
                    dos.writeUTF(fileName);
                }
                System.out.println("File list sent to Client " + clientId);
            } else {
                dos.writeInt(0);
                System.out.println("No files found for Client " + clientId);
            }

            dis.close();
            dos.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("File transfer error with Client " + clientId + ": " + e.getMessage());
        }
    }
}
