
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

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

public class Client_chat {

    public static void main(String[] args) throws IOException {

        System.out.println("Enter the port to connect to the server");
        Scanner sc = new Scanner(System.in);
        int port = sc.nextInt();
        Socket s = new Socket("localhost", port);
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        int encodedKey = dis.readInt();
        int decodedKey = (encodedKey - 3) << 2;
        dos.writeInt(decodedKey); 
        boolean verified = dis.readBoolean();
if (!verified) {
    System.out.println("Verification failed. Connection closed by server.");
    s.close();
    dis.close();
    dos.close();
    sc.close();
    return;
}

        String msgFromServer;
        String msgSent = "";
       
        System.out.println("Connected to server at port: " + port);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("You: ");
            msgSent = br.readLine();
            dos.writeUTF(msgSent);
            dos.flush();

            if (msgSent.equalsIgnoreCase("bye")) {
                System.out.println("You ended the chat.");
                break;
            }

            msgFromServer = dis.readUTF();
            if (msgFromServer.equalsIgnoreCase("bye")) {
                System.out.println("Server ended the chat.");
                break;
            }

            System.out.println("Server: " + msgFromServer);
        }

        br.close();
        s.close();
        dis.close();
        dos.close();
        sc.close();
    }
}
