package view;

import java.io.IOException;

import online.IRJogo;

public interface IBoardGameGUI extends IRJogo {
	public void fillBoard();
	public void showPossibleMoves();
	public void flushPossibleMoves();
	public void start();
	void notifyGame() throws IOException;
}
