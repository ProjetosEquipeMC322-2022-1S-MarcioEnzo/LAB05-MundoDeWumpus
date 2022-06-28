package online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Network implements INetwork {
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private Socket socket;
	private ServerSocket serverSocket;

	public Network(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Network(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.attach();
	}
	
	public void close()  {
		try {
			if (serverSocket != null)
				serverSocket.close();
			if (socket != null)
				socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(Object type) throws IOException {
		os.writeObject(type);
		os.flush();
	}
	
	public Object read() throws IOException {
		try {
			Object obj;	
			obj = is.readObject();
			return obj;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}

	public void attach() throws IOException {
			serverSocket.setSoTimeout(50000);
			socket = serverSocket.accept();
			is = new ObjectInputStream(socket.getInputStream());
			os = new ObjectOutputStream(socket.getOutputStream());
	}
}
