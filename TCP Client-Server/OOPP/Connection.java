import java.io.*;
import java.net.*;

public class Connection {

    private ServerSocket ss;
    private Socket clientSocket;

    public Connection(int port) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Server Started on port:" + port);

    }

    public void clientConnection() throws IOException {
        System.out.println("Waiting for client...");
        int client=0;
        while(true){
        clientSocket = ss.accept();
        client++;
        System.out.println("Client "+client+ "is connected..");
        }
    }

    public void close() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (ss != null) {
            ss.close();
        }
        System.out.println("Server closed");
    }

    public void Chat() {
        try (
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream()); DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream()); BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
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
