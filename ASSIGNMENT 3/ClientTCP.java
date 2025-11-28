import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            Socket cs = new Socket("localhost", 9090);
            System.out.println("Server connected successfully!");

            DataInputStream dis = new DataInputStream(cs.getInputStream());
            DataOutputStream dos = new DataOutputStream(cs.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String request = "hi";

            while (!(request.equals("quit"))) {
                System.out.print("You: ");                
                String kbInput = br.readLine();
                dos.writeUTF(kbInput);
                dos.flush();

                request = dis.readUTF();
                System.out.println("Server: " + request);
            }

            System.out.println("Client disconnected.");

            dis.close();
            dos.close();
            br.close();
            cs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
