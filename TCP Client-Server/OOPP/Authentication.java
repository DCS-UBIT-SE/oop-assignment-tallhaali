import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Authentication {
    private int key;
    private int encodedKey;
    public Authentication(){
        this.key=(int)(Math.random()*10000);
        this.encodedKey=(key>>2)+3;
    }
    public void sendKey(Socket clientSocket) throws IOException{
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        dos.writeInt(encodedKey);
        dos.flush();
        System.out.println("Waiting for verification...");
   }
   public boolean verification(Socket clientSocket) throws IOException{
    DataInputStream dis= new DataInputStream(clientSocket.getInputStream());
    int clientMsg=dis.readInt();
    if(clientMsg==key){
        System.out.println("Verification Successful");
        return true;
    }
    else{
        System.out.println("Verification failed");
        return false;
    }
   }
    
}
