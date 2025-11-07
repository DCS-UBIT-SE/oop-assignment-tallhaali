import java.io.*;
import java.net.*;

public class ChatHandler implements Runnable {

    private Socket clientSocket;
    private int clientId;

    public ChatHandler(Socket clientSocket, int clientId) {
        this.clientSocket = clientSocket;
        this.clientId = clientId;
    }

    @Override
    public void run() {
        try (
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) 
        ) {
            String msgRec, msgSent;
            while (true) {
                msgRec = dis.readUTF();
                System.out.println("Client " + clientId + ": " + msgRec);

                if (msgRec.equalsIgnoreCase("bye")) {
                    System.out.println("Client " + clientId + " ended the chat.");
                    dos.writeUTF("bye");
                    break;
                }

                System.out.print("Server (Client " + clientId + "): ");
                msgSent = br.readLine();
                dos.writeUTF(msgSent);

                if (msgSent.equalsIgnoreCase("bye")) {
                    System.out.println("You ended chat with Client " + clientId);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Chat error with client " + clientId + ": " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {}
        }
    }
}
