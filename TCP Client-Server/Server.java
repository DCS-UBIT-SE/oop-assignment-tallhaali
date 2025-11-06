import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
            ////////////
            //OBECTIVE//
            ///////////

// 1. Create a server program.
// 2. Make the server listen on a specific port (for example, 1000).
// 3. Display a message saying it’s waiting for a client connection.
// 4. Wait until a client connects.
// 5. Once connected, display that the client has connected.
// 6. Get input and output streams from the connection.
// 7. Receive a message from the client.
// 8. Print the received message on the server’s screen.
// 9. Prepare a reply that says “I have received your following message” followed by the client’s message.
// 10. Send this reply back to the client.
// 11. Close the input and output streams.
// 12. Close the client socket.
// 13. Close the server socket.


public class Server {

    public static void main(String[] args) throws Exception {
        ServerSocket ss =new ServerSocket(9090);
        DataInputStream dis;
        DataOutputStream dos;
        System.out.println("Waiting for client");
        Socket s =ss.accept();
        System.out.println("Client connected");
        String msgFromClient;
        String msgFromServer;

        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        msgFromClient=dis.readUTF();
        System.out.println("Client said:\n\t"+msgFromClient);

        msgFromServer="I have received your following message:"+msgFromClient;
        dos.writeUTF(msgFromServer);
        dis.close();
        dos.close();
        s.close();
        ss.close();

    }
}