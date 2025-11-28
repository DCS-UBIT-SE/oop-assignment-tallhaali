import java.io.*;
import java.net.*;

// This version waits one client 
// recieves one message from the client
// sends back the message "I have received your following message\n\t" alongwith the recieved message from the client 
// and ends the process

class TcpServerV1 
{
	public static void main(String[] args) throws Exception
	{
		ServerSocket ss = new ServerSocket(9090);
		Socket s;// = ss.accept();

		DataInputStream dis ;
		DataOutputStream dos ;

        String replyFromServer;
        String rcvdFromClient;

        System.out.println ("Waiting for client connection ...");

        s = ss.accept();
        System.out.println ("Client connected");

        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        rcvdFromClient = dis.readUTF();
        System.out.println("Client Said: " + rcvdFromClient);

        replyFromServer = "I have received your following message: \""+rcvdFromClient+"\"";
        dos.writeUTF(replyFromServer);
        dos.flush();

        dis.close();
        dos.close();
		
        s.close();
		ss.close();
            
    }

}