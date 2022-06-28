package online;

import java.io.IOException;

public interface IServer {
	public void attach() throws IOException;

	void send(Object obj) throws IOException;
}
