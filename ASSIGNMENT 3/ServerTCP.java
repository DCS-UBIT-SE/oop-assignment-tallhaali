import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9090);
            System.out.println("Server started at " + ss.getLocalPort());
            Socket cs = ss.accept();
            System.out.println("Client connected successfully!");

            DataInputStream dis = new DataInputStream(cs.getInputStream());
            DataOutputStream dos = new DataOutputStream(cs.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String request = "";

            while (!(request.equals("quit"))) {
                request = dis.readUTF();
                System.out.println("Client: " + request);
    
                System.out.print("You: ");
                String kbInput = br.readLine();
                dos.writeUTF(kbInput);
                dos.flush();
            }

            System.out.println("Server disconnected.");

            dis.close();
            dos.close();
            br.close();
            cs.close();
            ss.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
