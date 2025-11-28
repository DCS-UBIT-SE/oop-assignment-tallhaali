package clientservercomm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    String ipAddress;
    int port;
    Socket clientSocket;
    BufferedReader servReader;
    PrintWriter servWriter;

    public Client(String ipaddress, int port) {
        this.ipAddress = ipaddress;
        this.port = port;
    }

    public String connectServer() {
        String message = "";

        try {
            this.clientSocket = new Socket(this.ipAddress, this.port);
            servReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            servWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            message = "Client connected to " + this.ipAddress + ":" + this.port + " successfully";
        } catch (Exception e) {
            message = e.toString();
        }

        return message;
    }

    public void sendRequest(String msg) {
        servWriter.println(msg);
    }

    public String getResponse() {
        String res;
        try {
            res = servReader.readLine();
        } catch (Exception e) {
            res = e.getMessage();
        }
        return res;
    }

    public String terminateClient() {
        String message = "";
        try {
            clientSocket.close();
            message = "Client terminated successfully!";
        } catch (Exception e) {
            message = e.toString();
        }

        return message;
    }
}