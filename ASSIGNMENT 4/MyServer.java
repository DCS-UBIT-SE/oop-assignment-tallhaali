import clientservercomm.*;
import java.util.*;
import java.io.File;

public class MyServer {
    public static void main(String[] args) {
        
        Server server = new Server(9090);
        System.out.println(server.startServer());
        System.out.println(server.acceptClient());

        boolean cont = true;
        do {
            // Accepting request from client
            String request = server.getRequest();
            cont = isContinuing(request);

            // Handling client's requests
            String response = handleRequest(request);
            server.sendResponse(response);

        } while (cont);

        // Terminating the server
        server.terminateServer();

    }

    // Main function that handles the user requests
    public static String handleRequest(String req) {
        String res = "";
        String command = returnCommand(req); // getting the actual command from the request

        // HELP command handling
        if (command.equals("help")) {
            res = "HELP\t\t\tLists all available commands<nl>FILES \t\t\tLists all available files on the server<nl>GET <FILE-NAME> \tPrints the desired file<nl>QUIT \t\t\tQuits and shuts down the server";
        }        

        // GET command handling
        else if (command.contains("get")) {
            res = getFile(req);
        }

        // FILES command handling
        else if (command.equals("files")) {
            res = listFileNames();
        }

        // QUIT command handling
        else if (command.equals("quit")) {
            res = "quit";
        }

        // Invalid command handling
        else {
            res = "Please enter a valid command!";
        }

        System.out.println("Client: " + command);
        return res;
    }

    // Removes extra spaces and lower cases the string to return as command
    public static String returnCommand(String msg) {
        msg = msg.toLowerCase();
        msg = msg.trim();
        return msg;
    }

    // Returns if the client is continuing or quitting the server
    public static boolean isContinuing(String msg) {
        boolean cont = true;

        msg = returnCommand(msg);

        if (msg.equals("quit")) {
            return false;
        }

        return cont;
    }

    // Returns the list of file names from the current directory
    public static String listFileNames() {
        String directoryPath = "C:/Users/Abdullah/CLionProjects/java projects"; // Current directory
        File directory = new File(directoryPath);
        String output = "";

        // Check if the path exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles(); // Get array of File objects

            if (files != null) {
                int i = 1;
                output = output + "File names: ";
                for (File file : files) {
                    if (file.isFile()) { // Check if it's a file (not a subdirectory)
                        output = output + "<nl>" + i + ". [FIL] " + file.getName();
                    } else if (file.isDirectory()) { // Check if it's a file (not a subdirectory)
                        output = output + "<nl>" + i + ". [DIR] " + file.getName();

                    }
                    i++;
                }
            }
        } else {
            output = "Directory not found or is not a directory.";
        }

        System.out.println(output);
        return output;
    }

    // Returns the desired file as a string object
    public static String getFile(String msg) {
        msg = msg.replace("get ", "");
        String filename = msg;
        try {
            File file = new File(filename);

            if (!(file.exists())) {
                return "File does not exist!";
            }

            else if (!(file.isFile())) {
                return filename + " is not a file";
            }

            else {
                Scanner reader = new Scanner(file);

                String contents = "";
                while (reader.hasNextLine()) {
                    contents = contents + reader.nextLine() + "<nl>";
                }

                reader.close();
                return contents;
            }

        }

        catch (Exception e) {
            return e.toString();
        }
    }
}
