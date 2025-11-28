package clientservercomm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int port;
    ServerSocket serverSocket;
    BufferedReader clientReader;
    PrintWriter clientWriter;
    Socket clientSocket;

    public Server(int port) {
        this.port = port;
    }

    public String startServer() {
        String message = "";
        try {
            serverSocket = new ServerSocket(port);
            message = "Server has started!";
        } catch (Exception e) {
            message = e.toString();
        }

        return message;
    }

    public String acceptClient () {
        String message = "";

        try {
            clientSocket = serverSocket.accept();
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            message = "Client connected successfully!";
        } catch (Exception e) {
            message = e.toString();
        }

        return message;
    }

    public void sendResponse(String msg) {
        clientWriter.println(msg);
    }

    public String getRequest() {
        String res;
        try {
            res = clientReader.readLine();
        } catch (Exception e) {
            res = e.toString();
        }
        return res;
    }

    public String terminateServer() {
        String message = "";
        try {
            clientSocket.close();
            serverSocket.close();

            message = "Server terminated successfully!";
        } catch (Exception e) {
            message = e.toString();
        }

        return message;
    }

}
