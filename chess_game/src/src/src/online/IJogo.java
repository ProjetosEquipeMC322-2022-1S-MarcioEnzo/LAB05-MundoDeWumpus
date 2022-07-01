package online;

import java.io.IOException;

import control.ChessMatch;

public interface IJogo extends IRNetwork{
	public boolean estadoDeJogo();
	public int decidir();
	public void enviarPartida(ChessMatch chessMatch) throws IOException;
	public ChessMatch receberPartida() throws IOException;
}
