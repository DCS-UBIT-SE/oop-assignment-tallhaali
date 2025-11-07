import java.io.*;
import java.net.*;

public class ChatHandler implements Runnable {

    private Socket clientSocket;

    public ChatHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
                System.out.println("Client: " + msgRec);

                if (msgRec.equalsIgnoreCase("bye")) {
                    System.out.println("Client ended the chat.");
                    break;
                }

                System.out.print("Server: ");
                msgSent = br.readLine();
                dos.writeUTF(msgSent);
                dos.flush();

                if (msgSent.equalsIgnoreCase("bye")) {
                    System.out.println("You ended the chat.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Chat error: " + e.getMessage());
        }
    }
}
