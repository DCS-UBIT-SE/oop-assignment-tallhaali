import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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


public class FTP {

    public static void main(String[] args) throws Exception {
        ServerSocket ss =new ServerSocket(9090);
        DataInputStream dis;
        DataOutputStream dos;
        File currentDir =new File(".");
        String[] File=currentDir.list();
        String msgFromClient;
        String msgFromServer;
        Socket s =ss.accept();
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        System.out.println("Waiting for client");
        
        System.out.println("Client connected");

        msgFromServer="Please choose 1 for chat and 2 for File transfer";
        dos.writeUTF(msgFromServer);
        
        msgFromClient=dis.readUTF();
        if(msgFromClient.equalsIgnoreCase("1")){
    msgFromServer = "Welcome to chatBot";
    dos.writeUTF(msgFromServer);
} else if(msgFromClient.equalsIgnoreCase("2")){
    msgFromServer = "Welcome to File Transfer";
    dos.writeUTF(msgFromServer);
}

        System.out.println("Client said:\n\t"+msgFromClient);

        msgFromServer="I have received your following message:"+msgFromClient;
        dos.writeUTF(msgFromServer);
        dis.close();
        dos.close();
        s.close();
        ss.close();

    }
}