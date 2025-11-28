import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient {
    public static void main(String[] args) {
        String ipAddress = "localhost";
        int port = 9090;
        Socket clientSocket;
        BufferedReader servReader;
        PrintWriter servWriter;
        Scanner scanner = new Scanner(System.in);


        try {
            clientSocket = new Socket(ipAddress, port);
            servReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            servWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("Client connected to Chat Server (" + ipAddress + ":" + port + ") successfully");

            String msg = servReader.readLine();
            System.out.print(msg);
            String username = scanner.nextLine();
            servWriter.println(username);
            msg = servReader.readLine();
            System.out.println(msg);
            clientMsgs clientMsgs = new clientMsgs(servReader);
            clientMsgs.start();

            
            while (true) {
                String chatMessage = scanner.nextLine();
                if (chatMessage == null) {
                    break;
                }
                servWriter.println(chatMessage);
                if (chatMessage.equalsIgnoreCase("quit")) {
                    break;
                }
            }

            System.out.println("Disconnecting from chat server...");

            try {
                clientSocket.close();
            } catch (IOException e) {
                // ignore close errors
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}

class clientMsgs extends Thread {
    BufferedReader servReader;

    clientMsgs (BufferedReader sReader) {
        servReader = sReader;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = servReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Connection to server lost.");
        }
    }
}