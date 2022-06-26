import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class Network implements INetwork {
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private ServerSocket serversocket;
		
	public void send(String st) throws IOException {
		out.println(st);
		out.flush();
	}
	
	public void send(int x) throws IOException {
		out.println(x);
		out.flush();
	}
	
	public String read() throws IOException {
		String st = null;
		if(in != null) {
			st = in.readLine();
		}
		return st;
		}
	
	Network (int port) throws IOException {
		serversocket = new ServerSocket(port);
		this.attach();
	}
	public void attach() throws IOException {
		serversocket.setSoTimeout(50000);
		socket = serversocket.accept();
		System.out.println("jogador conectado");
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream());
	}

	Network (String ip, int port) {
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
