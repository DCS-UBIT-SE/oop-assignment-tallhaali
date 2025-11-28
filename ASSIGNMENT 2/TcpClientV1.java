import java.io.*;
import java.net.*;

class TcpClientV1
{
	public static void main(String[] args) throws Exception
	{
		Socket s = new Socket("localhost", 9090);
		DataInputStream dis = new DataInputStream(s.getInputStream());
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String stream="";
		String msgForServer="";

		System.out.print("Write your message for the server :");
		msgForServer = br.readLine();

		dos.writeUTF(msgForServer);
		dos.flush();

		stream = dis.readUTF();
		System.out.println("Response from Server is : " + stream);

		br.close();
		dos.close();
		dis.close();
		s.close();
	}
}