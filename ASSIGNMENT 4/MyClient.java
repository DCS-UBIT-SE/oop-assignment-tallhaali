import java.io.IOException;
import java.util.Scanner;

import clientservercomm.*;



public class MyClient {
    public static void main(String[] args) throws IOException {

        Client client = new Client("localhost", 9090);
        System.out.println(client.connectServer());

        // Setting up client's reader
        Scanner reader = new Scanner(System.in);

        boolean cont = true;

        do {
            System.out.print("Enter command: ");
            String command = reader.nextLine();

            // Sending the command to server
            client.sendRequest(command);
            
            // Recieving the response from server
            String response = client.getResponse();

            // Terminating program on "quit"
            if (response.equals("quit")) {
                cont = false;
                System.out.println("System is quitting!");
            }

            // Displaying the response in formatted manner
            else {
                response = applyFormatting(response);
                System.out.println(response);
            }

        } while (cont);

        // Terminating the client
        client.terminateClient();
        reader.close();
    }

    // Text formatter according to system protocols
    public static String applyFormatting(String txt) {
        txt = txt.replace("<nl>", "\n");
        return txt;
    }
}
