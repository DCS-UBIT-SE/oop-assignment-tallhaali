
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
            ////////////
            //OBECTIVE//
            ///////////

// 1. Create a client program.
// 2. Connect the client to the server running on the same computer (`localhost`) using port 1000.
// 3. Prepare input and output streams to communicate with the server.
// 4. Prepare a way to take user input from the keyboard.
// 5. Ask the user to type a message for the server.
// 6. Read the user’s message.
// 7. Send that message to the server.
// 8. Wait to receive a reply from the server.
// 9. Display the reply on the screen.
// 10. Close the input reader.
// 11. Close the data input and output streams.
// 12. Close the socket connection.

public class Client1 {
    public static void main(String[] args) throws IOException{
        
        Socket s= new Socket("localhost",9090);
        
        String msgFromServer;
        String msgSent;
        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dos=new DataOutputStream(s.getOutputStream());
        System.out.println("Connected to server");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter your message to send");
        msgSent=br.readLine();

        dos.writeUTF(msgSent);
        dos.flush();
        msgFromServer=dis.readUTF();
        System.out.println("Message received by server\n\t"+msgFromServer);

        br.close();
        s.close();
        dis.close();
        dos.close();


        
    }
}
