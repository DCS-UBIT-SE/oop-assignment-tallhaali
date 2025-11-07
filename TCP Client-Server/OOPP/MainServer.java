
import java.util.Scanner;




public class MainServer {
    public static void main(String[] args) throws Exception{
        
        Scanner userInput =new Scanner(System.in);
        System.out.print("Enter the port to start the server: ");
        int userPort= userInput.nextInt();
        Connection server =new Connection(userPort);
        server.startServer();
        server.close();
    }
    
}
