package online;

import java.io.IOException;

public interface INetwork extends IServer {
		public void send(Object obj) throws IOException;
		public Object read() throws IOException;
		public void close();
}
