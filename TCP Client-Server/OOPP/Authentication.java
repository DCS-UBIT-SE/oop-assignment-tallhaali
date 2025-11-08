import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Authentication {
    private int key;
    private int encodedKey;

    public Authentication() {
        this.key = (int)(Math.random() * 10000);
        this.encodedKey = (key * 4) + 3;

    }

    public void sendKey(DataOutputStream dos) throws IOException {
        dos.writeInt(encodedKey);
        dos.flush();
        System.out.println("Sent encoded key: " + encodedKey);
        System.out.println("Waiting for verification...");
    }

    public boolean verification(DataInputStream dis) throws IOException {
        int clientMsg = dis.readInt();
        System.out.println("Received decoded key: " + clientMsg);
        if (clientMsg == key) {
            System.out.println("Verification Successful");
            return true;
        } else {
            System.out.println("Verification failed (expected " + key + ")");
            return false;
        }
    }
}

