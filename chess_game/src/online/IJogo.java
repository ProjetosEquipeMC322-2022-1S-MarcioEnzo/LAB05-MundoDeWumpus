package online;

import java.io.IOException;

public interface IJogo extends IRNetwork{
	public boolean estadoDeJogo();
	public int decidir();
	public void comunicaPlayer() throws IOException;
	public int[] action();
	public void receberComando();
}
