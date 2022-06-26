import java.io.IOException;

public interface INetwork extends IServer, ICliente{
	public void send(String st) throws IOException;
	public void send(int x) throws IOException;
	public String read() throws IOException;
}
